package  hotelapp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Class HotelData - a data structure that stores information about hotels and
 * hotel reviews. Allows to quickly lookup a hotel given the hotel id. 
 * Allows to easily find hotel reviews for a given hotel, given the hotelID. 
 * Reviews for a given hotel id are sorted by the date and user nickname.
 *
 */
public class HotelData {

	private TreeMap<String,Hotel> hotelList;
	private TreeMap<String,HashMap<String,Review>> reviewList;

	/**
	 * Default constructor.
	 */
	public HotelData() {
		// Initialize all data structures
        this.hotelList=new TreeMap<String,Hotel>();
        this.reviewList=new TreeMap<String,HashMap<String, Review>>();
	}
	public HotelData(TreeMap<String,Hotel> hotelList,TreeMap<String,HashMap<String,Review>> reviewList){
		this.hotelList=hotelList;
		this.reviewList=reviewList;
	}

	/**
	 * Create a Hotel given the parameters, and add it to the appropriate data
	 * structure(s).
	 * 
	 * @param hotelId
	 *            - the id of the hotel
	 * @param hotelName
	 *            - the name of the hotel
	 * @param city
	 *            - the city where the hotel is located
	 * @param state
	 *            - the state where the hotel is located.
	 * @param streetAddress
	 *            - the building number and the street
	 * @param lat
	 * @param lon
	 */
	public void addHotel(String hotelId, String hotelName, String city, String state, String streetAddress, double lat,double lon,String country) {
		if(!this.hotelList.containsKey(hotelId)){
			Address newAddress=new Address(streetAddress,state,city,lon,lat,country);
			Hotel newHotel=new Hotel(hotelId,hotelName,newAddress);
			this.hotelList.put(hotelId,newHotel);
			this.reviewList.put(hotelId,new HashMap<String,Review>());
		}
	}

	/**
	 * Add a new review.
	 * 
	 * @param hotelId
	 *            - the id of the hotel reviewed
	 * @param reviewId
	 *            - the id of the review
	 * @param rating
	 *            - integer rating 1-5.
	 * @param reviewTitle
	 *            - the title of the review
	 * @param review
	 *            - text of the review
	 * @param isRecom
	 *            - whether the user recommends it or not
	 * @param date
	 *            - date of the review in the format yyyy-MM-dd, e.g.
	 *            2016-08-29.
	 * @param username
	 *            - the nickname of the user writing the review.
	 * @return true if successful, false if unsuccessful because of invalid date
	 *         or rating. Needs to catch and handle ParseException if the date is invalid.
	 *         Needs to check whether the rating is in the correct range
	 */
	public boolean addReview(String hotelId, String reviewId, int rating, String reviewTitle, String review,boolean isRecom, String date, String username) {
		boolean isSuccess=false;
        Review newReview=new Review(reviewId,hotelId,reviewTitle,review,username,date,rating,isRecom);
        if(this.reviewList.containsKey(hotelId)){
        	if(newReview.getOverallRating()>=1&&newReview.getOverallRating()<=5){
				HashMap<String,Review> reviews= this.reviewList.get(hotelId);
				if(!reviews.containsKey(newReview.getReviewId())){
					isSuccess=reviews.put(newReview.getReviewId(),newReview)!=null?true:false;
				}
			}
		}
		return isSuccess;
	}

	/**
	 * Add reviews in batch
	 * @param reviews
	 */
	public void addReviewBatch(TreeMap<String,HashMap<String,Review>> reviews){
		for(String s:reviews.keySet()){
			HashMap<String,Review> rs=reviews.get(s);
			this.reviewList.get(s).putAll(rs);
		}
	}
	/**
	 * Return an alphabetized list of the ids of all hotels
	 * 
	 * @return
	 */
	public List<String> getHotels() {
		List<String> listId=new ArrayList<String>();
		for(String id:this.hotelList.keySet()){
			listId.add(id);
		}
		Collections.sort(listId);
        return listId;
	}

	/**
	 * Read the json file with information about the hotels (id, name, address,
	 * etc) and load it into the appropriate data structure(s). Note: This
	 * method does not load reviews
	 * 
	 * @param jsonFilename
	 *            the name of the json file that contains information about the
	 *            hotels
	 */
	public TreeMap<String,Hotel> loadHotelInfo(String jsonFilename) {
		TreeMap<String,Hotel> hotelListForMultiThread=new TreeMap<>();
		JSONParser parser = new JSONParser();
		try {
		    //get file path
			Path hotelJsonPath=Paths.get(jsonFilename);
            //convert to json object
            JSONObject jsonObject = (JSONObject)parser.parse(new FileReader(hotelJsonPath.toString()));
            //get hotel iterator
            JSONArray source=(JSONArray)jsonObject.get("sr");
            Iterator<JSONObject> iteratorObj=source.iterator();
            //loop to create hotelSet
            while(iteratorObj.hasNext()){
                JSONObject jsonHotel=iteratorObj.next();
                //get latitude and longitude
                JSONObject ll=(JSONObject)jsonHotel.get("ll");

                //create Address
                Address newAddress=new Address(
                        (String)jsonHotel.get("ad"),
                        (String)jsonHotel.get("pr"),
                        (String)jsonHotel.get("ci"),
                        Double.valueOf((String)ll.get("lng")),
                        Double.valueOf((String)ll.get("lat")),
						(String)jsonHotel.get("c")
                );
                //Create Hotel
                Hotel newHotel=new Hotel(
                        (String)jsonHotel.get("id"),
                        (String)jsonHotel.get("f"),
                        newAddress
                );
				this.reviewList.put(newHotel.getHotelId(),new HashMap<String,Review>());
                this.hotelList.put(newHotel.getHotelId(),newHotel);
				hotelListForMultiThread.put(newHotel.getHotelId(),newHotel);
            }
            //How does set decide if the element has already been contained in the set??
		}
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        finally {
        	return hotelListForMultiThread;
		}
	}

	/**
	 * Load reviews for all the hotels into the appropriate data structure(s).
	 * Traverse a given directory recursively to find all the json files with
	 * reviews and load reviews from each json. Note: this method must be
	 * recursive and use DirectoryStream as discussed in class.
	 * 
	 * @param path
	 *            the path to the directory that contains json files with
	 *            reviews Note that the directory can contain json files, as
	 *            well as subfolders (of subfolders etc..) with more json files
	 */
	public void loadReviews(Path path) {
		ArrayList<String> files=findFiles(path);
		//Parse json files
		for(String file:files){
			jsonParseToRview(Paths.get(file));
		 }
	}

	/**
	 * Returns a string representing information about the hotel with the given
	 * id, including all the reviews for this hotel separated by
	 * -------------------- Format of the string: HoteName: hotelId
	 * streetAddress city, state -------------------- Review by username: rating
	 * ReviewTitle ReviewText -------------------- Review by username: rating
	 * ReviewTitle ReviewText ...
	 * 
	 * @param hotelId
	 *            id
	 * @return - output string.
	 */
	public String toString(String hotelId) {
        StringBuilder output=new StringBuilder();
	    if(this.hotelList.containsKey(hotelId)){
            output.append(FormatString.PrintHotel(this.hotelList.get(hotelId)));
            output.append(FormatString.PrintReviews(new ArrayList<Review>(this.reviewList.get(hotelId).values())));
        }

//        if(hotelId.equals("9329")){
//            FormatString.PrintReviews(this.reviewList.get(hotelId));
//        }

		return output.toString();
	}

	/**
	 * Save the string representation of the hotel data to the file specified by
	 * filename in the following format: 
	 * an empty line 
	 * A line of 20 asterisks ******************** on the next line 
	 * information for each hotel, printed in the format described in the toString method of this class.
	 * 
	 * @param filename
	 *            - Path specifying where to save the output.
	 */
	public void printToFile(Path filename) {
		try {
			//create dir and file
			if(!Files.exists(filename.getParent())){
				Files.createDirectory(filename.getParent());
			}
			if(!Files.exists(filename)){
				Files.createFile(filename);
			}
			//writ to file
			PrintWriter writer = new PrintWriter(new FileWriter(filename.toString()));
			StringBuilder output=new StringBuilder("\n");
			for(String k:this.hotelList.keySet()){
				output.append(FormatString.HOTEL_SEPERATOR +"\n");
				output.append(this.toString(k)+"\n");
			}
			writer.print(output);
			writer.flush();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * get all the files in a given directory
	 * @param dir the object directory
	 * @return a list of files
	 */
	public ArrayList<String> findFiles(Path dir){
		ArrayList<String> filesList=new ArrayList<String>();
		try {
			//recurrence of the entire directory
			DirectoryStream<Path> pathsList = Files.newDirectoryStream(dir);
			for (Path path : pathsList) {
				if (!Files.isDirectory(path)) {
					filesList.add(path.toString());
				}
				else {
					ArrayList<String> childFiles=findFiles(path);
					if(childFiles!=null) filesList.addAll(childFiles);
				}
			}
			return filesList;
		} catch (IOException e) {
			System.out.println("IOException occurred");
			return null;
		}
	}

	/**
	 * Parse the json file into Review class
	 * @param filePath the path of the json file
	 * @return
	 */
	public List<Review> jsonParseToRview(Path filePath){
		JSONParser parser = new JSONParser();
		List<Review> reviewsForMultiThread=new ArrayList<>();
		try {
			//convert to json object
			JSONObject jsonObject = (JSONObject)parser.parse(new FileReader(filePath.toString()));
			//get review collection
			JSONObject reviewDetails=(JSONObject)jsonObject.get("reviewDetails");
			JSONObject reviewCollection=(JSONObject)reviewDetails.get("reviewCollection");
			JSONArray reviews=(JSONArray)reviewCollection.get("review");
			//get iterator
			Iterator<JSONObject> iteratorObj=reviews.iterator();
			//loop to create reviews
			while(iteratorObj.hasNext()){
				JSONObject jsonReview=iteratorObj.next();
				//get rating integer
				Long ratingLong=(Long)jsonReview.get("ratingOverall");
				//get isRecommend
				String isRecommendStr=(String)jsonReview.get("isRecommended");
				boolean isRecommendBool=isRecommendStr=="YES"?true:false;
				//parse username
				String name=(String)jsonReview.get("userNickname");
				name=name.equals("")||name.equals(null)?Review.ANONYMOUS:name;
				//create review
				Review newReview=new Review(
						(String)jsonReview.get("reviewId"),
						(String)jsonReview.get("hotelId"),
						(String)jsonReview.get("title"),
						(String)jsonReview.get("reviewText"),
						name,
						//FormatString.formateDate((String)jsonReview.get("reviewSubmissionTime")),
						(String)jsonReview.get("reviewSubmissionTime"),
						ratingLong.intValue(),
						isRecommendBool
				);
				//filter the review
				//if hotel id is contained
				if(this.hotelList.containsKey(newReview.getHotelId())){
					if(!this.reviewList.containsKey(newReview.getHotelId())){
						this.reviewList.put(newReview.getHotelId(),new HashMap<String,Review>());
					}
					//if the overall rating is valid
					if(newReview.getOverallRating()>=1&&newReview.getOverallRating()<=5){
						HashMap<String,Review> Hotelreviews=this.reviewList.get(newReview.getHotelId());
						//if the reviews is not duplicate
						if(!Hotelreviews.containsKey(newReview.getReviewId())){
							Hotelreviews.put(newReview.getReviewId(),newReview);
							reviewsForMultiThread.add(newReview);
						}
					}
				}
			}
		}
		catch (FileNotFoundException e) {
			System.out.println(filePath.toString());
			e.printStackTrace();
		}
		catch (IOException e) {
			System.out.println(filePath.toString());
			e.printStackTrace();
		}
		catch (ParseException e) {
			System.out.println(filePath.toString());
			e.printStackTrace();
		}
		finally {
			return reviewsForMultiThread;
		}
	}
	/***************************************Json output begin**************************************/
	/**
	 * return hotel info format by json
	 * @param hotelId
	 * @return
	 */
	public String getHotelJson(String hotelId){
		JSONObject obj=new JSONObject();
		if(hotelId==null) {
			obj.put("success",false);
			obj.put("hotelId","invalid");
			return obj.toJSONString();
		}
		Hotel h=this.hotelList.get(hotelId);


		//create json
		if(h==null) {
			obj.put("success",false);
			obj.put("hotelId","invalid");
		}
		else{
			Address add=h.getAddressInfo();
			obj.put("success",true);
			obj.put("hotelId",hotelId);
			obj.put("name",h.getHotelName());
			obj.put("addr",add.getStreetAddress());
			obj.put("city",add.getCity());
			obj.put("state",add.getState());
			obj.put("lat",add.getLatitude());
			obj.put("lng",add.getLongitude());
			obj.put("country",add.getCountry());
		}
		return obj.toJSONString();
	}

	/**
	 * get # of reviews in particular hotel
	 * @param hotelId hotel id
	 * @param nums # of reviews
	 * @return
	 */
	public String getReviewsJson(String hotelId,int nums){
		JSONObject obj=new JSONObject();
		JSONArray reviews=new JSONArray();

		if(hotelId==null||nums==0){
			obj.put("success",false);
			obj.put("hotelId","invalid");
			return obj.toJSONString();
		}

		Hotel h=this.hotelList.get(hotelId);

		//create json
		if(h==null) {
			obj.put("success",false);
			obj.put("hotelId","invalid");
		}
		else{
			List<Review> reviewList=new ArrayList<>(this.reviewList.get(hotelId).values());
			Collections.sort(reviewList, new Comparator<Review>() {
				@Override
				public int compare(Review r1, Review r2) {
					DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					try{
						Date o1Date=df.parse(r1.getDate());
						Date o2Date=df.parse(r2.getDate());

						if(o1Date.after(o2Date)) return 1;
						else if(o1Date.before(o2Date)) return -1;
						else if(r1.getUserName().compareTo(r2.getUserName())!=0){
							return r1.getUserName().compareTo(r2.getUserName());
						}
						else{
							return r1.getReviewId().compareTo(r2.getReviewId());
						}
					}
					catch (java.text.ParseException e){
						e.printStackTrace();
					}
					return 0;
				}
			});
			for(Review r:reviewList){
				if(nums==0) break;
				JSONObject robj=new JSONObject();
				robj.put("reviewId",r.getReviewId());
				robj.put("title",r.getReviewTitle());
				robj.put("user",r.getUserName());
				robj.put("reviewText",r.getReviewText());
				robj.put("date",r.getDate());
				reviews.add(robj);
				nums--;
			}
			obj.put("reviews",reviews);
			obj.put("hotelId",hotelId);
			obj.put("success",true);
		}
		return obj.toJSONString();
	}

	public TreeMap<String,Hotel> gethotelList(){
		return this.hotelList;
	};
	public TreeMap<String,HashMap<String,Review>> getreviewList(){
		return this.reviewList;
	};
	/***************************************Json output end**************************************/
}
