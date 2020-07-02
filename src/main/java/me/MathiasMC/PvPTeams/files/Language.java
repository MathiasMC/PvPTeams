package me.MathiasMC.PvPTeams.files;

import me.MathiasMC.PvPTeams.PvPTeams;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Language {

    public FileConfiguration get;
    private final File file;

    private final PvPTeams plugin;

    public Language(final PvPTeams plugin) {
        this.plugin = plugin;
        file = new File(plugin.getDataFolder(), "language.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException exception) {
                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
            }
        }
        load();
        update();
    }

    public void load() {
        get = YamlConfiguration.loadConfiguration(file);
    }

    private void update() {
        boolean change = false;
        plugin.textUtils.fileHeader(get);
        if (!get.contains("player.pvpteams.command.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou dont have access to use this command!");
            get.set("player.pvpteams.command.permission", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.command.unknown")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cUnknown sub command &f{pvpteams_command}");
            get.set("player.pvpteams.command.unknown", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.command.unknown")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cUnknown sub command &f{pvpteams_command}");
            get.set("console.pvpteams.command.unknown", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.command.message")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&c&m---------------------------------------------");
            list.add("&7&l> &aPvPTeams created by &eMathiasMC");
            list.add("&7&l> &aVersion: &e{pvpteams_version}");
            list.add("&7&l> &f/pvpteams help for list of commands");
            list.add("&7&l> &2Any ideas for the plugin or need help?");
            list.add("&7&l> &2Contact me on spigot");
            list.add("&c&m---------------------------------------------");
            get.set("player.pvpteams.command.message", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.command.message")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&c&m---------------------------------------------");
            list.add("&7&l> &aPvPTeams created by &eMathiasMC");
            list.add("&7&l> &aVersion: &e{pvpteams_version}");
            list.add("&7&l> &f/pvpteams help for list of commands");
            list.add("&7&l> &2Any ideas for the plugin or need help?");
            list.add("&7&l> &2Contact me on spigot");
            list.add("&c&m---------------------------------------------");
            get.set("console.pvpteams.command.message", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.help.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou dont have access to use this command!");
            get.set("player.pvpteams.help.permission", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.help.message")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&c&m---------------------------------------------");
            list.add("&7&l> &f/pvpteams reload");
            list.add("&7&l> &f/pvpteams save");
            list.add("&7&l> &f/pvpteams create <teamName>");
            list.add("&7&l> &f/pvpteams delete");
            list.add("&7&l> &f/pvpteams invite <player>");
            list.add("&7&l> &f/pvpteams kick <player>");
            list.add("&7&l> &f/pvpteams accept");
            list.add("&7&l> &f/pvpteams gui open <fileName> <player>");
            list.add("&7&l> &f/pvpteams message <player> <text>");
            list.add("&7&l> &f/pvpteams quest <quest> add <amount> <player>");
            list.add("&7&l> &f/pvpteams member/coins/teamcoins add/remove/set <amount> <player>");
            list.add("&c&m---------------------------------------------");
            get.set("player.pvpteams.help.message", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.help.message")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&c&m---------------------------------------------");
            list.add("&7&l> &f/pvpteams reload");
            list.add("&7&l> &f/pvpteams save");
            list.add("&7&l> &f/pvpteams gui open <fileName> <player>");
            list.add("&7&l> &f/pvpteams message <player> <text>");
            list.add("&7&l> &f/pvpteams quest <quest> add <amount> <player>");
            list.add("&7&l> &f/pvpteams member/coins/teamcoins add/remove/set <amount> <player>");
            list.add("&c&m---------------------------------------------");
            get.set("console.pvpteams.help.message", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.save.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou dont have access to use this command!");
            get.set("player.pvpteams.save.permission", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.save.saved")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &aSaved cached data to the database!");
            get.set("player.pvpteams.save.saved", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.save.saved")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &aSaved cached data to the database!");
            get.set("console.pvpteams.save.saved", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.reload.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou dont have access to use this command!");
            get.set("player.pvpteams.reload.permission", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.reload.reloaded")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &aReloaded all configs!");
            get.set("player.pvpteams.reload.reloaded", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.reload.reloaded")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &aReloaded all configs!");
            get.set("console.pvpteams.reload.reloaded", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.create.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou dont have access to use this command!");
            get.set("player.pvpteams.create.permission", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.create.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cUsage: /pvpteams create <teamName>");
            get.set("player.pvpteams.create.usage", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.create.already")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou already have a team");
            get.set("player.pvpteams.create.already", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.create.member")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou cannot create a team you are already in the team {pvpteams_team}");
            get.set("player.pvpteams.create.member", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.create.taken")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cThat team name is already taken!");
            get.set("player.pvpteams.create.taken", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.create.name")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cThe team name can only contain a-z");
            get.set("player.pvpteams.create.name", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.create.created")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &eCreated team &f{pvpteams_team}");
            get.set("player.pvpteams.create.created", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.delete.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou dont have access to use this command!");
            get.set("player.pvpteams.delete.permission", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.delete.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cUsage: /pvpteams delete");
            get.set("player.pvpteams.delete.usage", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.delete.no-team")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou dont have a team!");
            get.set("player.pvpteams.delete.no-team", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.delete.member")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYour team owner has deleted your team!");
            get.set("player.pvpteams.delete.member", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.delete.owner")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cOnly the owner of the team can delete your team");
            get.set("player.pvpteams.delete.owner", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.delete.deleted")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cDeleted your team &f{pvpteams_team}&c!");
            get.set("player.pvpteams.delete.deleted", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.create.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou cannot create a team as console");
            get.set("console.pvpteams.create.usage", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.delete.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou cannot delete a team as console");
            get.set("console.pvpteams.delete.usage", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.invite.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou cannot invite as console");
            get.set("console.pvpteams.invite.usage", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.accept.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou cannot accept as console");
            get.set("console.pvpteams.accept.usage", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.invite.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou dont have access to use this command!");
            get.set("player.pvpteams.invite.permission", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.accept.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou dont have access to use this command!");
            get.set("player.pvpteams.accept.permission", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.invite.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cUsage: /pvpteams invite <player>");
            get.set("player.pvpteams.invite.usage", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.invite.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cThat player is not online!");
            get.set("player.pvpteams.invite.online", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.invite.team")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou dont have a team!");
            get.set("player.pvpteams.invite.team", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.invite.already-team")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cThat player is already in a team");
            get.set("player.pvpteams.invite.already-team", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.invite.only")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cOnly the owner of the team can invite!");
            get.set("player.pvpteams.invite.only", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.invite.player")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cThat player is already in your team!");
            get.set("player.pvpteams.invite.player", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.invite.already")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cThat player has a invite already try again soon...");
            get.set("player.pvpteams.invite.already", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.invite.dont")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &eThe invite for the team &f{pvpteams_team} &ehas expired you didn't accept the invite");
            get.set("player.pvpteams.invite.dont", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.invite.invited")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &eYou have got an invite to join &f{pvpteams_team} &e/pvpteams accept");
            get.set("player.pvpteams.invite.invited", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.invite.invited-owner")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &eYou have invited &f{pvpteams_team_player} &eto your team &f{pvpteams_team}");
            get.set("player.pvpteams.invite.invited-owner", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.accept.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cUsage: /pvpteams accept");
            get.set("player.pvpteams.accept.usage", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.accept.team")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou are already in a team!");
            get.set("player.pvpteams.accept.team", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.accept.invite")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou dont have any invites!");
            get.set("player.pvpteams.accept.invite", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.accept.accepted")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &eYou are now a member of &f{pvpteams_team}");
            get.set("player.pvpteams.accept.accepted", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.accept.accepted-owner")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &f{pvpteams_team_player} &eis now a member of your team");
            get.set("player.pvpteams.accept.accepted-owner", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.kick.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou dont have access to use this command!");
            get.set("player.pvpteams.kick.permission", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.kick.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cUsage: /pvpteams kick <player>");
            get.set("player.pvpteams.kick.usage", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.kick.team")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou dont have a team!");
            get.set("player.pvpteams.kick.team", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.kick.only")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cOnly the owner of the team can kick!");
            get.set("player.pvpteams.kick.only", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.kick.player")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou dont have that player in your team!");
            get.set("player.pvpteams.kick.player", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.kick.kicked-member")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou have been kicked by the team owner!");
            get.set("player.pvpteams.kick.kicked-member", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.kick.kicked-owner")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou have kicked &f{pvpteams_team_player}");
            get.set("player.pvpteams.kick.kicked-owner", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.kick.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou cannot kick as console");
            get.set("console.pvpteams.kick.usage", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.gui.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cUsage: /pvpteams gui open");
            get.set("player.pvpteams.gui.usage", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.gui.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cUsage: /pvpteams gui open");
            get.set("console.pvpteams.gui.usage", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.gui.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou dont have access to use this command!");
            get.set("player.pvpteams.gui.permission", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.gui.open.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou dont have access to use this command!");
            get.set("player.pvpteams.gui.open.permission", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.gui.open.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cUsage: /pvpteams gui open <fileName> <player>");
            get.set("player.pvpteams.gui.open.usage", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.gui.open.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cUsage: /pvpteams gui open <fileName> <player>");
            get.set("console.pvpteams.gui.open.usage", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.gui.open.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cThat player is not online!");
            get.set("player.pvpteams.gui.open.online", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.gui.open.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cThat player is not online!");
            get.set("console.pvpteams.gui.open.online", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.gui.open.found")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cThe file {pvpteams_gui_file} is not found");
            get.set("player.pvpteams.gui.open.found", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.gui.open.found")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cThe file {pvpteams_gui_file} is not found");
            get.set("console.pvpteams.gui.open.found", list);
            change = true;
        }
        if (!get.contains("player.pvpmenu.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou dont have access to use this command!");
            get.set("player.pvpmenu.permission", list);
            change = true;
        }
        if (!get.contains("player.pvpmenu.commands")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("pvpteams gui open menu.yml {pvpteams_player}");
            get.set("player.pvpmenu.commands", list);
            change = true;
        }
        if (!get.contains("player.pvpmenu.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cUsage: /pvpmenu <player>");
            get.set("player.pvpmenu.usage", list);
            change = true;
        }
        if (!get.contains("console.pvpmenu.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cUsage: /pvpmenu <player>");
            get.set("console.pvpmenu.usage", list);
            change = true;
        }
        if (!get.contains("console.pvpmenu.commands")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("pvpteams gui open menu.yml {pvpteams_player}");
            get.set("console.pvpmenu.commands", list);
            change = true;
        }
        if (!get.contains("player.pvpmenu.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cThat player is not online!");
            get.set("player.pvpmenu.online", list);
            change = true;
        }
        if (!get.contains("console.pvpmenu.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cThat player is not online!");
            get.set("console.pvpmenu.online", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.quest.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou dont have access to use this command!");
            get.set("player.pvpteams.quest.permission", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.quest.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cUsage: /pvpteams quest <quest> add");
            get.set("player.pvpteams.quest.usage", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.quest.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cUsage: /pvpteams quest <quest> add");
            get.set("console.pvpteams.quest.usage", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.quest.find")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cCannot find that quest!");
            get.set("player.pvpteams.quest.find", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.quest.find")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cCannot find that quest!");
            get.set("console.pvpteams.quest.find", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.quest.add.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cUsage: /pvpteams quest <quest> add <amount> <player>");
            get.set("player.pvpteams.quest.add.usage", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.quest.add.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cUsage: /pvpteams quest <quest> add <amount> <player>");
            get.set("console.pvpteams.quest.add.usage", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.quest.add.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cThat player is not online!");
            get.set("player.pvpteams.quest.add.online", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.quest.add.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cThat player is not online!");
            get.set("console.pvpteams.quest.add.online", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.quest.add.team")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cThe player does not have a team!");
            get.set("player.pvpteams.quest.add.team", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.quest.add.team")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cThe player does not have a team!");
            get.set("console.pvpteams.quest.add.team", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.quest.add.number")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cNot a number!");
            get.set("player.pvpteams.quest.add.number", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.quest.add.number")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cNot a number!");
            get.set("console.pvpteams.quest.add.number", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.quest.add.message")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &eYou have added &a{pvpteams_quest_add} &eto team &a{pvpteams_team}");
            get.set("player.pvpteams.quest.add.message", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.quest.add.message")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &eYou have added &a{pvpteams_quest_add} &eto team &a{pvpteams_team}");
            get.set("console.pvpteams.quest.add.message", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.message.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou dont have access to use this command!");
            get.set("player.pvpteams.message.permission", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.message.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cUsage: /pvpteams message <player> <text>");
            get.set("player.pvpteams.message.usage", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.message.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cUsage: /pvpteams message <player> <text>");
            get.set("console.pvpteams.message.usage", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.message.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cThat player is not online!");
            get.set("player.pvpteams.message.online", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.message.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cThat player is not online!");
            get.set("console.pvpteams.message.online", list);
            change = true;
        }










        if (!get.contains("player.pvpteams.add-remove-set.permission")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cYou dont have access to use this command!");
            get.set("player.pvpteams.add-remove-set.permission", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.add-remove-set.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cUsage: /pvpteams member/coins/teamcoins add/remove/set <amount> <player>");
            get.set("player.pvpteams.add-remove-set.usage", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.add-remove-set.usage")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cUsage: /pvpteams member/coins/teamcoins add/remove/set <amount> <player>");
            get.set("console.pvpteams.add-remove-set.usage", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.add-remove-set.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cThat player is not online!");
            get.set("player.pvpteams.add-remove-set.online", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.add-remove-set.online")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cThat player is not online!");
            get.set("console.pvpteams.add-remove-set.online", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.add-remove-set.number")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cNot a number!");
            get.set("player.pvpteams.add-remove-set.number", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.add-remove-set.number")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cNot a number!");
            get.set("console.pvpteams.add-remove-set.number", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.add-remove-set.team")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cThe target does not have a team!");
            get.set("player.pvpteams.add-remove-set.team", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.add-remove-set.team")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cThe target does not have a team!");
            get.set("console.pvpteams.add-remove-set.team", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.add-remove-set.0")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cCannot be under 0!");
            get.set("player.pvpteams.add-remove-set.0", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.add-remove-set.0")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &cCannot be under 0!");
            get.set("console.pvpteams.add-remove-set.0", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.add-remove-set.members")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &e{pvpteams_team} members is now at {pvpteams_members}");
            get.set("player.pvpteams.add-remove-set.members", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.add-remove-set.members")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &eYour team {pvpteams_team} members is now at {pvpteams_members}");
            get.set("console.pvpteams.add-remove-set.members", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.add-remove-set.teamcoins")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &e{pvpteams_team} teamcoins is now at {pvpteams_teamcoins}");
            get.set("player.pvpteams.add-remove-set.teamcoins", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.add-remove-set.teamcoins")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &eYour team {pvpteams_team} teamcoins is now at {pvpteams_teamcoins}");
            get.set("console.pvpteams.add-remove-set.teamcoins", list);
            change = true;
        }
        if (!get.contains("player.pvpteams.add-remove-set.coins")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &e{pvpteams_player} coins is now at {pvpteams_coins}");
            get.set("player.pvpteams.add-remove-set.coins", list);
            change = true;
        }
        if (!get.contains("console.pvpteams.add-remove-set.coins")) {
            ArrayList<String> list = new ArrayList<>();
            list.add("&7[&aPvPTeams&7] &eYou now have {pvpteams_coins} coins");
            get.set("console.pvpteams.add-remove-set.coins", list);
            change = true;
        }
        if (change) {
            try {
                get.save(file);
            } catch (IOException exception) {
                plugin.textUtils.exception(exception.getStackTrace(), exception.getMessage());
            }
            plugin.textUtils.info("language.yml ( A change was made )");
        } else {
            plugin.textUtils.info("language.yml ( Loaded )");
        }
    }
}
