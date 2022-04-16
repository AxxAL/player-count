package net.axxal.playercount.api;

import net.axxal.playercount.interfaces.IPluginAccess;
import java.util.List;

public class ApiAuthenticator implements IPluginAccess {
    private static final List<?> authorizedRemotes = plugin.getConfig().getList("socket-api.authorized-remotes");

    public static boolean isAuthorized(String remote) {
        assert authorizedRemotes != null;
        return authorizedRemotes.contains(remote);
    }
}
