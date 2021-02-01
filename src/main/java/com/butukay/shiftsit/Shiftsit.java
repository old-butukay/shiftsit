package com.butukay.shiftsit;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public final class Shiftsit extends JavaPlugin {

    public static JSONObject playerDetails = new JSONObject();

    @Override
    public void onEnable() {
        this.getCommand("shiftsit").setExecutor(new ShiftsitCommand());
        getServer().getPluginManager().registerEvents(new EventHandlers(), this);

        if (!new File(this.getDataFolder(), "config.yml").exists()) {
            this.saveDefaultConfig();
        }

        File jsonFile = new File(this.getDataFolder(), "shiftsit.json");

        try {
            if (!jsonFile.createNewFile()) {

                JSONParser jsonParser = new JSONParser();

                FileReader reader = new FileReader(jsonFile.getPath());
                Object obj = jsonParser.parse(reader);

                playerDetails = (JSONObject) obj;

            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        File jsonFile = new File(this.getDataFolder(), "shiftsit.json");

        try (FileWriter file = new FileWriter(jsonFile.getPath())) {

            file.write(playerDetails.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
