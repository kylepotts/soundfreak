package com.deadbeef.soundfreq.interfaces;

import java.io.FileDescriptor;

/**
 * Created by kyle on 7/25/15.
 */
public interface OnFileDownloadedListener {

    public void onFileDownloaded(FileDescriptor fd);
}
