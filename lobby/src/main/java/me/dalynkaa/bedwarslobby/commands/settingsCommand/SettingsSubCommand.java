package me.dalynkaa.bedwarslobby.commands.settingsCommand;

import me.dalynkaa.bedwarslobby.proxyUtils.data.player.BPlayer;

import java.util.List;

public abstract class SettingsSubCommand {

    public abstract String getName();

    public abstract String getDescription();

    public abstract String getSyntax();

    public abstract void perform(BPlayer bPlayer, String args[]);

    public abstract List<String> getSubcommandArguments(BPlayer bPlayer, String args[]);

}
