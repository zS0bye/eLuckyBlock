package it.zS0bye.eLuckyBlock.commands;

import it.zS0bye.eLuckyBlock.eLuckyBlock;
import it.zS0bye.eLuckyBlock.utils.*;
import net.md_5.bungee.api.chat.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainCommand implements CommandExecutor {

    private final eLuckyBlock plugin;

    public MainCommand(final eLuckyBlock plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command cmd, @NotNull String commandLabel, String[] args) {

        if(!cmd.getName().equalsIgnoreCase("eluckyblock")) {
            return true;
        }

        if (!sender.hasPermission("luckyblock.command")) {

            if (!VersionChecker.getV1_8()
            && !VersionChecker.getV1_9()
            && !VersionChecker.getV1_10()
            && !VersionChecker.getV1_11()
            && !VersionChecker.getV1_12()
            && !VersionChecker.getV1_13()
            && !VersionChecker.getV1_14()
            && !VersionChecker.getV1_15()) {
                notPermsHex(sender);
                return true;
            }

            Player player = (Player) sender;
            notPermsLegacy(player);
            return true;
        }

        this.plugin.reloadConfig();
        this.plugin.getLang().saveDefaultConfig();
        this.plugin.getLucky().saveDefaultConfig();
        this.plugin.getAnimations().saveDefaultConfig();
        this.plugin.getRewards().saveDefaultConfig();
        for (String luckyblock : this.plugin.getLucky().getConfig().getKeys(false)) {
            LuckyUtils utils = new LuckyUtils(luckyblock);
            String LuckyReward = utils.getString(utils.getRewards());
            new RewardUtils(luckyblock, LuckyReward);
        }
        LangUtils.RELOAD_CONFIGURATION.send(sender);

        return true;
    }

    private void notPermsHex(final CommandSender sender) {
        String resource = "&#c89cf7%lC&#ca9cf3%lL&#cc9cef%lI&#ce9ceb%lC&#d09ce8%lK &#d49be0%lT&#d69bdc%lO &#db9bd5%lO&#dd9bd1%lP&#df9bcd%lE&#e19bca%lN &#e59ac2%lT&#e79abe%lH&#e99aba%lE &#ee9ab3%lR&#f099af%lE&#f299ab%lS&#f499a8%lO&#f699a4%lU&#f899a0%lR&#fa999c%lC&#fc9999%lE"
                .replace("&", "")
                .replace("%l", "&l");
        String hover = "&#22f82e%nC&#23f340%nl&#24ee52%ni&#26e964%nc&#27e476%nk &#29da9a%nM&#2bd5ac%ne"
                .replace("&", "")
                .replace("%n", "&n");
        notPermsHex().forEach(msg -> sender.sendMessage(ColorUtils.sendCentered(msg)));
        BaseComponent[] clickLink = TextComponent.fromLegacyText(ColorUtils.sendCentered(resource));
        BaseComponent[] hoverLink = TextComponent.fromLegacyText(ColorUtils.getColor(hover));
        ComponentBuilder hoverBuilder = new ComponentBuilder("");
        hoverBuilder.append(hoverLink);
        HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverBuilder.create());
        ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/eluckyblock.97759/");
        for(BaseComponent component : clickLink) {
            component.setHoverEvent(hoverEvent);
            component.setClickEvent(clickEvent);
        }
        ComponentBuilder textBuilder = new ComponentBuilder("");
        textBuilder.append(clickLink);
        sender.spigot().sendMessage(textBuilder.create());
        sender.sendMessage("");
    }

    private void notPermsLegacy(final Player player) {
        String resource = "&d&lCLICK TO OPEN THE RESOURCE";
        String hover = "&a&nClick me!";
        notPermsLegacy().forEach(msg -> player.sendMessage(ColorUtils.sendCentered(msg)));
        TextComponent clickLink = new TextComponent(ColorUtils.sendCentered(resource));
        clickLink.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ColorUtils.getColor(hover)).create()));
        clickLink.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://www.spigotmc.org/resources/eluckyblock.97759/"));
        player.spigot().sendMessage(clickLink);
        player.sendMessage("");
    }

    private List<String> notPermsLegacy() {
        List<String> msg = new ArrayList<>();
        msg.add("");
        msg.add("&5ₑLuckyBlock v" + this.plugin.getDescription().getVersion());
        msg.add("&7Developed by &dzS0bye");
        msg.add("");
        msg.add("&7Rate our service by giving us");
        msg.add("&7a positive review &e★★★★&7!");
        msg.add("");
        return msg;
    }

    private List<String> notPermsHex() {
        String luckyBlock = "&#270050ₑ&#2d005bL&#320066u&#380071c&#3e007ck&#430087y&#45008bB&#4a0094l&#4f009do&#5300a7c&#5800b0k"
                .replace("&", "");
        String developedBy = "&#8cb0ffD&#87a4ffe&#8298ffv&#7d8cffe&#7880ffl&#7374ffo&#6e67ffp&#695bffe&#644fffd &#5a37ffb&#562bffy"
                .replace("&", "");
        String author = "&#6c31fbz&#7e3ffcS&#8f4dfd0&#a15bfdb&#b269fey&#c377ffe"
                .replace("&", "");
        String stars = "&#e7d740★&#d3bb33★&#bf9e25★&#ab8218★"
                .replace("&", "");
        List<String> msg = new ArrayList<>();
        msg.add("");
        msg.add(luckyBlock + " v" + this.plugin.getDescription().getVersion());
        msg.add(developedBy + " " + author);
        msg.add("");
        msg.add("&7Rate our service by giving us");
        msg.add("&7a positive review " + stars + "&7!");
        msg.add("");
        return msg;
    }

}