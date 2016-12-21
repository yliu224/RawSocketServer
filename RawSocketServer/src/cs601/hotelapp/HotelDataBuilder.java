package cs601.hotelapp;

import cs601.concurrent.WorkQueue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by dean on 9/28/2016. Lab3 Part2
 */
public class HotelDataBuilder {
    private volatile ThreadSafeHotelData threadSafeHotel;
    private volatile TreeMap<String,Hotel> HotelDataList;
    private WorkQueue workQueue;
    private volatile int numOfTasks;

    public HotelDataBuilder(ThreadSafeHotelData data){
        threadSafeHotel=data;
        this.workQueue=new WorkQueue(10);
        this.numOfTasks =0;
    }
    public HotelDataBuilder(ThreadSafeHotelData data, WorkQueue queue){
        this.threadSafeHotel=data;
        this.workQueue=queue;
        this.numOfTasks =0;
    }

    public int getNumOfTasks() {
        return numOfTasks;
    }

    /**
     * This method is not thread safe!!This works on single thread ONLY!
     */
    public void loadHotelInfo(String fileName){
        HotelDataList=this.threadSafeHotel.loadHotelInfo(fileName);
    }

    /**
     * print the review data to file
     * @param dir file name
     */
    public void printToFile(Path dir){
        waitUntilFinished();
        this.threadSafeHotel.printToFile(dir);
    }
    /*******************************Multiple Thread Functions Begin**************************************/
    private synchronized void incrementTask(){
        this.numOfTasks++;
    }
    private synchronized void decrementTask(){
        this.numOfTasks--;
        //if(this.numOfTasks<=0) notifyAll();
        notifyAll();
    }
    public synchronized void waitUntilFinished() {
        while (this.numOfTasks > 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * merge reviews to the main reviewList
     * @param reviewsList the reviews which are extracted from a json file
     */
    public synchronized void mergeReviews(TreeMap<String,HashMap<String,Review>> reviewsList){
        this.threadSafeHotel.addReviewBatch(reviewsList);
    }

    /**
     * merge reviews to the main reviewList
     * @param reviewsList a List<Review>
     */
    public synchronized void mergeReviews(List<Review> reviewsList){
        for(Review r:reviewsList){
            this.threadSafeHotel.addReview(r.getHotelId(),r.getReviewId(),r.getOverallRating(),r.getReviewTitle(),r.getReviewText(),r.getIsRecom(),r.getDate(),r.getUserName());
        }
    }
    /**
     * Wait until there is no pending work, then shutdown the queue
     */
    public synchronized void shutdown() {
        waitUntilFinished();
        workQueue.shutdown();
    }
    /**
     * Processes a given directory: creates a new FileWorker  and adds it to the work queue.
     *
     * @param directory
     */
    public void loadReviews(Path directory) {
        try {
            for (Path path : Files.newDirectoryStream(directory)) {
                if (Files.isDirectory(path)) {
                    //recursive call to find deeper directories
                    loadReviews(path);
                } else {
                    //parse the file with FileWorker
                    if(path.toString().endsWith("json")){
                        workQueue.execute(new FileWorker(path));
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*******************************Multiple Thread Functions End**************************************/
    /*******************************Runnable Class Begin**************************************/
    class FileWorker implements Runnable{
        private Path directory;
        private ThreadSafeHotelData tsHotelData;
        FileWorker(Path dir) {
            this.directory = dir;
//            TreeMap<String,HashMap<String,Review>> reviewList=new TreeMap<>();
//            for(String s:threadSafeHotel.getHotels()){
//                reviewList.put(s,new HashMap<>());
//            }
            this.tsHotelData=new ThreadSafeHotelData(HotelDataList,new TreeMap<String,HashMap<String,Review>>());
            incrementTask();
            //System.out.println(numOfTasks+"\t"+dir.toString()+"\tIncrement");
        }
        @Override
        public void run() {
            try {
                List<Review> rs= tsHotelData.jsonParseToRview(this.directory);
                //TreeMap<String,HashMap<String,Review>> reviewList=tsHotelData.getReviewList();
                mergeReviews(rs);
            } catch (Exception e) {
                //System.out.println(e.getMessage());
                e.printStackTrace();
            }
            finally {
                decrementTask();
                //System.out.println(numOfTasks+"\t"+directory.toString()+"\tDecrement");
            }
        }

    }
    /*******************************Runnable Class End**************************************/
}
