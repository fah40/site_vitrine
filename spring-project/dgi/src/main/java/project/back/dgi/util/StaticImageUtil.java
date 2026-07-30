package project.back.dgi.util;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class StaticImageUtil {

    // clé utilisée côté front/formulaire -> sous-dossier dédié sous file.static-images-dir
    public static final Map<String, String> KEYS_TO_DIRS = Map.of(
            "pdc", "pdc",
            "safi", "safi",
            "torohy", "torohy",
            "2call", "2call"
    );

    public static Map<String, String> resolveAll(String baseDir) {
        Map<String, String> result = new LinkedHashMap<>();
        for (String cle : KEYS_TO_DIRS.keySet()) {
            result.put(cle, resolve(baseDir, cle));
        }
        return result;
    }

    public static String resolve(String baseDir, String cle) {
        String subDir = KEYS_TO_DIRS.get(cle);
        if (subDir == null) {
            return "";
        }

        File dir = new File(baseDir, subDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String path = "";
        File[] existingFiles = dir.listFiles();
        if (existingFiles != null && existingFiles.length > 0) {
            String fileName = existingFiles[0].getName(); // Nom du fichier uniquement
            path = "/uploads/static-images/" + subDir + "/" + fileName; // Chemin web relatif
        }

        return path;
    }
}
