package it.zS0bye.eLuckyBlock.files;

import com.google.common.io.ByteStreams;
import it.zS0bye.eLuckyBlock.eLuckyBlock;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

@Getter
public class LanguagesFile {

    private final eLuckyBlock plugin;

    private FileConfiguration config;
    private final File file;

    public LanguagesFile(eLuckyBlock plugin) {

        this.plugin = plugin;
        this.file = new File(this.plugin.getDataFolder(), "messages.yml");

        this.saveDefaultConfig();
    }

    public void saveDefaultConfig() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        if (!file.exists()) {
            try {
                file.createNewFile();

                InputStream is = plugin.getResource("messages.yml");
                OutputStream os = new FileOutputStream(file);

                ByteStreams.copy(is, os);

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

}