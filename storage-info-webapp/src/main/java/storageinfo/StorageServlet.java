package storageinfo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

@WebServlet("/storage")
public class StorageServlet extends HttpServlet {

    private static class StorageInfo {
        String driveName;
        long totalSize;
        long freeSpace;

        public StorageInfo(String driveName, long totalSize, long freeSpace) {
            this.driveName = driveName;
            this.totalSize = totalSize;
            this.freeSpace = freeSpace;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<StorageInfo> storageInfos = new ArrayList<>();
        File[] roots = File.listRoots();
        for (File root : roots) {
            storageInfos.add(new StorageInfo(root.getAbsolutePath(), root.getTotalSpace(), root.getFreeSpace()));
        }

        Gson gson = new Gson();
        String json = gson.toJson(storageInfos);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.print(json);
        out.flush();
    }
}
