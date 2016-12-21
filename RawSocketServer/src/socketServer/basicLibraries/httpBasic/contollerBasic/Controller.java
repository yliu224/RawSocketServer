package  socketServer.basicLibraries.httpBasic.contollerBasic;

import  socketServer.basicLibraries.httpBasic.annotations.Get;
import  socketServer.basicLibraries.httpBasic.models.ActionResult;

/**
 * Must return ActionResult
 */
public abstract class Controller {
    @Get
    public abstract ActionResult index();
}
