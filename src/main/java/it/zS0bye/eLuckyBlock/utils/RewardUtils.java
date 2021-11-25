package it.zS0bye.eLuckyBlock.utils;

import it.zS0bye.eLuckyBlock.eLuckyBlock;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

@Getter
public class RewardUtils extends FileUtils {

    private final FileConfiguration rewards;
    private final String chance;
    private final String execute;

    public RewardUtils(final String luckyblock, final String reward) {
        this.rewards = eLuckyBlock.getInstance().getRewards().getConfig();
        this.chance = luckyblock + "." + reward + ".chance";
        this.execute = luckyblock + "." + reward + ".execute";
    }

    protected String getPrefix() {
        return ConfigUtils.SETTINGS_PREFIX.getString();
    }

    public String getString(final String path) {
        return ColorUtils.getColor(this.rewards.getString(path));
    }

    public boolean equals(final String path, final String equals) {
        return this.rewards.getString(path).equalsIgnoreCase(equals);
    }

    public boolean getBoolean(String path) {
        return this.rewards.getBoolean(path);
    }

    public List<String> getStringList(final String path) {
        List<String> list = new ArrayList<>();
        for(String setList : this.rewards.getStringList(path)) {
            list.add(ColorUtils.getColor(setList));
        }
        return list;
    }

    public boolean contains(String path) {
        return this.rewards.contains(path);
    }

    public int getInt(final String path) {
        return this.rewards.getInt(path);
    }
}
