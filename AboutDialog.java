package com.adam.aslfms;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class AboutDialog {
	private static final String TAG = "AboutDialog";
	private final Context mCtx;

	public AboutDialog(Context ctx) {
		super();
		this.mCtx = ctx;
	}

	public void show() {
		final LayoutInflater factory = LayoutInflater.from(mCtx);

		View dialogView = factory.inflate(R.layout.about, null);

		innerUpdate(dialogView);

		AlertDialog.Builder adBuilder = new AlertDialog.Builder(mCtx).setTitle(
				R.string.about).setIcon(
				android.R.drawable.ic_dialog_info).setView(dialogView)
				.setPositiveButton(R.string.whats_new,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								new WhatsNewDialog(mCtx).show();
							}
						}).setNegativeButton(R.string.close,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});

		adBuilder.show();
	}

	private void innerUpdate(View dialogView) {
		TextView appName = (TextView) dialogView.findViewById(R.id.app_name);
		TextView author = (TextView) dialogView.findViewById(R.id.author);
		TextView license = (TextView) dialogView.findViewById(R.id.license);
		TextView whatIsThis = (TextView) dialogView
				.findViewById(R.id.what_is_this);
		TextView website = (TextView) dialogView.findViewById(R.id.website);
		TextView email = (TextView) dialogView.findViewById(R.id.email);

		// app name & version
		PackageManager pm = mCtx.getPackageManager();
		String appText = "??";
		try {
			ApplicationInfo appInfo = pm.getApplicationInfo(mCtx
					.getPackageName(), 0);
			String label = pm.getApplicationLabel(appInfo).toString();
			label = label == null ? "??" : label;
			PackageInfo pkgInfo = pm.getPackageInfo(mCtx.getPackageName(), 0);
			appText = label + " v" + pkgInfo.versionName;
		} catch (NameNotFoundException e) {
			Log.e(TAG, "Couldn't read app info on own package!");
		}
		appName.setText(appText);
		
		// author
		author.setText(R.string.by_adam);
		
		// license
		license.setText(R.string.license);
		
		// text
		whatIsThis.setText(R.string.about_text);

		// website
		website.setText(mCtx.getString(R.string.website) + " "
				+ mCtx.getString(R.string.website_url));
		
		// email
		email.setText(mCtx.getString(R.string.contact) + " "
				+ mCtx.getString(R.string.email_addr));
	}

}