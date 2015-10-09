package com.xlj.erp.movefield.adapter;

import java.io.File;
import java.util.List;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xlj.erp.movefield.MApplication;
import com.xlj.erp.movefield.R;
import com.xlj.erp.movefield.entity.ProjectFile;
import com.xlj.erp.movefield.utils.AsyncTask;
import com.xlj.erp.movefield.utils.ToastUtils;

//TODO 尚未完成：正在进入，退出重新进入显示正在下载的进度；无效链接DownloadManager.COLUMN_TOTAL_SIZE_BYTES返回-1，下载大文件也可能返回-1，原因不明，暂时这样处理。
public class ProjectFileAdapter extends RecyclerView.Adapter<ProjectFileAdapter.ViewHolder> {
	private List<ProjectFile> mProjectFiles;
	private Context mContext;

	public static class ViewHolder extends RecyclerView.ViewHolder {
		View itemView;
		ImageView docTypeView;
		TextView docNameView;
		TextView uploadTimeView;
		ImageView isNewView;
		ProgressBar progressBar;

		public ViewHolder(View itemView) {
			super(itemView);
			this.itemView = itemView;
			docTypeView = (ImageView) itemView.findViewById(R.id.iv_item_project_file_doc_type);
			docNameView = (TextView) itemView.findViewById(R.id.tv_item_project_file_doc_name);
			uploadTimeView = (TextView) itemView.findViewById(R.id.tv_item_project_file_upload_time);
			isNewView = (ImageView) itemView.findViewById(R.id.iv_item_project_file_is_new);
			progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
		}
	}

	public ProjectFileAdapter(List<ProjectFile> projectFiles) {
		mProjectFiles = projectFiles;
	}

	@Override
	public int getItemCount() {
		return mProjectFiles.size();
	}

	@Override
	public void onBindViewHolder(final ViewHolder viewHolder, int i) {
		final ProjectFile file = mProjectFiles.get(i);

		int downloaded = checkDownloaded(file);

		if (file.getDocType().equals("Word")) {
			viewHolder.docTypeView.setImageResource(R.drawable.ic_project_file_type_word);

		} else if (file.getDocType().equals("Excel")) {
			viewHolder.docTypeView.setImageResource(R.drawable.ic_project_file_type_excel);

		} else if (file.getDocType().equals("Pdf")) {
			viewHolder.docTypeView.setImageResource(R.drawable.ic_project_file_type_pdf);

		} else if (file.getDocType().equals("Ppt")) {
			viewHolder.docTypeView.setImageResource(R.drawable.ic_project_file_type_ppt);
		}
		viewHolder.docNameView.setText(file.getDocName());
		viewHolder.uploadTimeView.setText(file.getUploadTime());
		if (file.getIsNew() == 1) {
			// 最新
			viewHolder.isNewView.setVisibility(View.VISIBLE);
			viewHolder.isNewView.setBackgroundResource(R.drawable.ic_project_file_tag_is_new);
		} else {
			viewHolder.isNewView.setVisibility(View.INVISIBLE);
		}

		OnClickListener listener = null;
		//TODO 这里的处理逻辑有问题
		if (downloaded == 1) {
			listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					// open();
					String filePath = Uri.parse(file.getDocUrl()).getPath();
					File f = new File(file.getDocUrl());
					openFile(f);
				}
			};
		} else if (downloaded == 0) {
			listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					int state = checkDownloaded(file);
					if (state == 0) {
						new DownloadTask().execute(file.getDocUrl(), viewHolder, file.getDocName());
					} else {
						ToastUtils.showToast(mContext, "正在下载中");
					}

				}
			};
		} else if (downloaded == 2) {
			listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					ToastUtils.showToast(mContext, "正在下载中");
				}
			};
		}

		viewHolder.itemView.setOnClickListener(listener);
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
		mContext = viewGroup.getContext();
		View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_project_file, viewGroup, false);
		ViewHolder vh = new ViewHolder(v);
		return vh;
	}

	/**
	 * 获取文件下载状态：0，未下载；1，已下载；2，正在下载
	 * 
	 * @param file
	 * @return
	 */
	private int checkDownloaded(ProjectFile file) {
		DownloadManager manager = (DownloadManager) MApplication.mApplication.getSystemService(Context.DOWNLOAD_SERVICE);
		DownloadManager.Query q = new DownloadManager.Query();
		// q.setFilterById(reference);
		Cursor cursor = manager.query(q);

		if (!cursor.moveToFirst()) {
			return 0;
		}

		do {
			int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
			if (file.getDocUrl().equals(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI))) && status == DownloadManager.STATUS_RUNNING) {
				return 2;
			}
			boolean isDownloadSuccess = (status == DownloadManager.STATUS_SUCCESSFUL);
			if (file.getDocUrl().equals(cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_URI))) && isDownloadSuccess) {
				String fileName = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
				file.setDocUrl(fileName);
				return 1;
			}
		} while (cursor.moveToNext());
		return 0;
	}

	private class DownloadTask extends AsyncTask<Object, Void, String> {
		ViewHolder viewHolder;
		String filePath = "";

		protected String doInBackground(Object... param) {
			// Do some work
			String url = (String) param[0];
			viewHolder = (ViewHolder) param[1];
			String docName = (String) param[2];

			Uri uri = Uri.parse(url);

			DownloadManager.Request request = new DownloadManager.Request(uri);
			// request.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_ONLY_COMPLETION);
			request.setTitle(docName);
			final DownloadManager manager = (DownloadManager) MApplication.mApplication.getSystemService(Context.DOWNLOAD_SERVICE);
			final long reference = manager.enqueue(request);

			boolean downloading = true;
			String info = "";
			DownloadManager.Query q = new DownloadManager.Query();
			q.setFilterById(reference);
			while (downloading) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				try {
					final Cursor cursor = manager.query(q);
					cursor.moveToFirst();
					int bytes_downloaded = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
					int bytes_total = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
					if (bytes_total == 0 || bytes_total == -1) {
						Log.v("yang", "bytes_total:" + bytes_total);
						downloading = false;
						info = "下载失败，服务器文件出错";
						manager.remove(reference);
						cursor.close();
						setProgress(viewHolder.progressBar, 0);
						break;
					}

					int status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
					if (status == DownloadManager.STATUS_SUCCESSFUL) {
						downloading = false;
						info = "下载完成，点击打开文件";
						filePath = cursor.getString(cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_FILENAME));
					} else if (status == DownloadManager.STATUS_FAILED) {
						downloading = false;
						info = "下载文件失败";
						manager.remove(reference);
						cursor.close();
						setProgress(viewHolder.progressBar, 0);
						break;
					} else if (status == DownloadManager.STATUS_PAUSED) {
						Log.v("yang", "status == DownloadManager.STATUS_PAUSED");
						downloading = false;
						info = "下载失败";
						manager.remove(reference);
						cursor.close();
						setProgress(viewHolder.progressBar, 0);
						break;
					}

					final int dl_progress = (int) ((bytes_downloaded * 100l) / bytes_total);
					setProgress(viewHolder.progressBar, dl_progress);
					Log.e("____________" + bytes_total, statusMessage(cursor));

					// 要是执行了break，cursor肯定不能关闭。。。。
					cursor.close();
				} catch (Exception e) {
					downloading = false;
					info = "下载失败";
					manager.remove(reference);
					setProgress(viewHolder.progressBar, 0);
					e.printStackTrace();
				}
			}

			return info;
		}

		protected void onPostExecute(String param) {
			// Print Toast or open dialog
			ToastUtils.showToast(mContext, param);
			if (!filePath.equals("")) {
				viewHolder.progressBar.setVisibility(View.INVISIBLE);

				viewHolder.itemView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// open()
						File f = new File(filePath);
						openFile(f);
					}
				});

			}
		}
	}

	private String statusMessage(Cursor c) {
		String msg = "???";

		switch (c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS))) {
		case DownloadManager.STATUS_FAILED:
			msg = "Download failed!";
			break;

		case DownloadManager.STATUS_PAUSED:
			msg = "Download paused!";
			break;

		case DownloadManager.STATUS_PENDING:
			msg = "Download pending!";
			break;

		case DownloadManager.STATUS_RUNNING:
			msg = "Download in progress!";
			break;

		case DownloadManager.STATUS_SUCCESSFUL:
			msg = "Download complete!";
			break;

		default:
			msg = "Download is nowhere in sight";
			break;
		}

		return (msg);
	}

	private void openFile(File file) {

		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		// 设置intent的Action属性
		intent.setAction(Intent.ACTION_VIEW);
		// 获取文件file的MIME类型
		String type = getMIMEType(file);
		// 设置intent的data和Type属性。
		String u = Uri.fromFile(file).toString();
		intent.setDataAndType(/* uri */Uri.fromFile(file), type);
		// 跳转
		mContext.startActivity(intent);
	}

	/**
	 * 根据文件后缀名获得对应的MIME类型。
	 * 
	 * @param file
	 */
	private String getMIMEType(File file) {

		String type = "*/*";
		String fName = file.getName();
		// 获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
		/* 获取文件的后缀名 */
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "")
			return type;
		// 在MIME和文件类型的匹配表中找到对应的MIME类型。
		for (int i = 0; i < MIME_MapTable.length; i++) { // MIME_MapTable??在这里你一定有疑问，这个MIME_MapTable是什么？
			if (end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}

	private final String[][] MIME_MapTable = {
			// {后缀名，MIME类型}
			{ ".3gp", "video/3gpp" }, { ".apk", "application/vnd.android.package-archive" }, { ".asf", "video/x-ms-asf" }, { ".avi", "video/x-msvideo" }, { ".bin", "application/octet-stream" },
			{ ".bmp", "image/bmp" }, { ".c", "text/plain" }, { ".class", "application/octet-stream" }, { ".conf", "text/plain" }, { ".cpp", "text/plain" }, { ".doc", "application/msword" },
			{ ".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document" }, { ".xls", "application/vnd.ms-excel" },
			{ ".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" }, { ".exe", "application/octet-stream" }, { ".gif", "image/gif" }, { ".gtar", "application/x-gtar" },
			{ ".gz", "application/x-gzip" }, { ".h", "text/plain" }, { ".htm", "text/html" }, { ".html", "text/html" }, { ".jar", "application/java-archive" }, { ".java", "text/plain" },
			{ ".jpeg", "image/jpeg" }, { ".jpg", "image/jpeg" }, { ".js", "application/x-javascript" }, { ".log", "text/plain" }, { ".m3u", "audio/x-mpegurl" }, { ".m4a", "audio/mp4a-latm" },
			{ ".m4b", "audio/mp4a-latm" }, { ".m4p", "audio/mp4a-latm" }, { ".m4u", "video/vnd.mpegurl" }, { ".m4v", "video/x-m4v" }, { ".mov", "video/quicktime" }, { ".mp2", "audio/x-mpeg" },
			{ ".mp3", "audio/x-mpeg" }, { ".mp4", "video/mp4" }, { ".mpc", "application/vnd.mpohun.certificate" }, { ".mpe", "video/mpeg" }, { ".mpeg", "video/mpeg" }, { ".mpg", "video/mpeg" },
			{ ".mpg4", "video/mp4" }, { ".mpga", "audio/mpeg" }, { ".msg", "application/vnd.ms-outlook" }, { ".ogg", "audio/ogg" }, { ".pdf", "application/pdf" }, { ".png", "image/png" },
			{ ".pps", "application/vnd.ms-powerpoint" }, { ".ppt", "application/vnd.ms-powerpoint" }, { ".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation" },
			{ ".prop", "text/plain" }, { ".rc", "text/plain" }, { ".rmvb", "audio/x-pn-realaudio" }, { ".rtf", "application/rtf" }, { ".sh", "text/plain" }, { ".tar", "application/x-tar" },
			{ ".tgz", "application/x-compressed" }, { ".txt", "text/plain" }, { ".wav", "audio/x-wav" }, { ".wma", "audio/x-ms-wma" }, { ".wmv", "audio/x-ms-wmv" },
			{ ".wps", "application/vnd.ms-works" }, { ".xml", "text/plain" }, { ".z", "application/x-compress" }, { ".zip", "application/x-zip-compressed" }, { "", "*/*" } };

	private void setProgress(final ProgressBar progressBar, final int progress) {
		((Activity) mContext).runOnUiThread(new Runnable() {

			@Override
			public void run() {

				progressBar.setProgress(progress);

			}
		});
	}

}
