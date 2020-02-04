package com.tokopedia.hotfixsdk.patch;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.meituan.robust.Patch;
import com.meituan.robust.PatchExecutor;
import com.meituan.robust.PatchManipulate;
import com.meituan.robust.RobustApkHashUtils;
import com.tokopedia.hotfixsdk.Config;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Author errysuprayogi on 01,February,2020
 */
public class PatchManipulateImp extends PatchManipulate {
    /***
     * connect to the network ,get the latest patches
     * @param context
     *
     * @return
     */

    @Override
    protected List<Patch> fetchPatchList(final Context context) {

        // Report the app's own robustApkHash to the server, and the server distinguishes each apk build to issue a patch to the app based on the robustApkHash
        // apkhash is the unique identifier for apk, so you cannnot patch wrong apk.
        String robustApkHash = RobustApkHashUtils.readRobustApkHash(context);
        Log.w("robust", "robustApkHash :" + robustApkHash);
        //connect to network to get patch list on servers
        //Go online to get the list of patches

        Patch patch = new Patch();
        patch.setName("123");
        int count;
        try {
            URL url = new URL(Config.PATCH_URL);

            URLConnection connection = url.openConnection();
            connection.connect();
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            OutputStream output = new FileOutputStream(context.getDir("patch",
                    Context.MODE_PRIVATE).getAbsolutePath() + File.separator + "patch.jar");
            byte data[] = new byte[1024];
            while ((count = input.read(data)) != -1) {
                // writing data to file
                output.write(data, 0, count);
            }
            output.flush();
            output.close();
            input.close();
            patch.setLocalPath(context.getDir("patch",
                    Context.MODE_PRIVATE).getAbsolutePath() + File.separator + "patch");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //we recommend LocalPath store the origin patch.jar which may be encrypted,while TempPath is the true runnable jar
        // LocalPath stores the original patch file. This file should be encrypted. TempPath is encrypted. The patch under TempPath will be deleted after loading to ensure security.
        // This needs to set some patch information, which is mainly the patch information obtained by networking. The important ones are MD5, simple verification of the original patch file, and the location of the patch storage. It is recommended to place the storage location of the patch in the application's private directory to ensure security.

        // setPatchesInfoImplClassFullName Each App can be customized independently. You need to ensure that the package name set by setPatchesInfoImplClassFullName is consistent with the xml configuration item patchPackname, and the class name must be: PatchesInfoImpl
        // Please note the settings here
        patch.setPatchesInfoImplClassFullName("com.meituan.robust.patch.PatchesInfoImpl");
        List patches = new ArrayList<Patch>();
        patches.add(patch);
        return patches;
    }

    /**
     * @param context
     * @param patch
     * @return you can verify your patches here
     */
    @Override

    protected boolean verifyPatch(Context context, Patch patch) {
        //do your verification, put the real patch to patch
        //放到app的私有目录
        patch.setTempPath(context.getCacheDir() + File.separator + "robust" + File.separator + "patch");
        //in the sample we just copy the file
        try {
            copy(patch.getLocalPath(), patch.getTempPath());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("copy source patch to local patch error, no patch execute in path " + patch.getTempPath());
        }

        return true;
    }

    public void copy(String srcPath, String dstPath) throws IOException {
        File src = new File(srcPath);
        if (!src.exists()) {
            throw new RuntimeException("source patch does not exist ");
        }
        File dst = new File(dstPath);
        if (!dst.getParentFile().exists()) {
            dst.getParentFile().mkdirs();
        }
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

    /**
     * @param patch
     * @return you may download your patches here, you can check whether patch is in the phone
     */
    @Override
    protected boolean ensurePatchExist(Patch patch) {
        return true;
    }
}