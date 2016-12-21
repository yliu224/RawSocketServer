package cs601.socketServer.basicLibraries.httpBasic.contollerBasic;

import cs601.socketServer.basicLibraries.httpBasic.annotations.Get;
import cs601.socketServer.basicLibraries.httpBasic.models.ActionResult;

/**
 * Must return ActionResult
 */
public abstract class Controller {
    @Get
    public abstract ActionResult index();
}
