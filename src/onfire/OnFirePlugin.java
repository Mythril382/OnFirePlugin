package onfire;

import arc.*;
import arc.util.*;
import mindustry.core.*;
import mindustry.entities.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;

import static mindustry.Vars.*;

public class OnFirePlugin extends Plugin{
    public static boolean fire = false;
    
    @Override
    public void init(){
        Events.run(Trigger.update, () -> {
            if(fire){
                Groups.player.each(p -> Fires.create(world.tile(World.conv(p.unit().x), World.conv(p.unit().y))));
            }
        });
    }
    
    @Override
    public void registerServerCommands(CommandHandler handler){
        handler.register("startfire", "Set all players on fire (if they are air-units, they will not be damaged).", args -> {
            fire = true;
            Log.info("Players will now burn.");
        });
        handler.register("stopfire", "Stop the fire.", args -> {
            fire = false;
            Log.info("Players will no longer burn.");
        });
    }
    
    @Override
    public void registerClientCommands(CommandHandler handler){
        handler.<Player>register("startfire", "Set all players on fire (if they are air-units, they will not be damaged).", (args, player) -> {
            if(!player.admin){
                player.sendMessage("[scarlet]You're not an admin...");
                return;
            }
            fire = true;
            player.sendMessage("Players will now burn.");
        });
        handler.<Player>register("stopfire", "Stop the fire.", (args, player) -> {
            if(!player.admin){
                player.sendMessage("[scarlet]You're not an admin...");
                return;
            }
            fire = false;
            Log.info("Players will no longer burn.");
        });
    }
}
