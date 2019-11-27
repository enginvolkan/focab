package com.engin.focab.services.impl;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.apache.xmlrpc.XmlRpcException;
import org.springframework.stereotype.Component;

import com.engin.focab.services.SubtitleService;
import com.github.wtekiela.opensub4j.api.OpenSubtitlesClient;
import com.github.wtekiela.opensub4j.impl.OpenSubtitlesClientImpl;
import com.github.wtekiela.opensub4j.response.Response;
import com.github.wtekiela.opensub4j.response.SubtitleFile;
import com.github.wtekiela.opensub4j.response.SubtitleInfo;

@Component
public class OpenSubtitlesOrgSubtitleService implements SubtitleService {

	private OpenSubtitlesClient client;
	private OpenSubtitlesClient osClient;

	private OpenSubtitlesClient getClient() {
		try {
			if (this.client == null) {
				URL serverUrl = new URL("https", "api.opensubtitles.org", 443, "/xml-rpc");
				client = new OpenSubtitlesClientImpl(serverUrl);

//			XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
//			config.setServerURL(serverUrl);
//			config.setConnectionTimeout(900);
//			config.setReplyTimeout(900);
//			config.setGzipCompressing(true);
//
//			this.client = new OpenSubtitlesClientImpl(config);
			}

			if (!client.isLoggedIn()) {
				Response response = client.login("envolkan", "4yu5hsbYq!7qnTU", "en", "envolkan");
				response.getStatus();

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return client;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.engin.focab.services.impl.SubtitleService#getASubtitleByImdbId(java.lang.
	 * String)
	 */
	@Override
	public SubtitleFile getASubtitleByImdbId(String id) {
		SubtitleFile subTitle = new SubtitleFile();
		try {
			subTitle = null;
			List<SubtitleInfo> list = getClient().searchSubtitles("eng", id);
			Optional<SubtitleInfo> subtitleInfo = list.stream().filter(x -> "srt".equals(x.getFormat()))
					.max(Comparator.comparingInt(SubtitleInfo::getDownloadsNo));
			if (subtitleInfo.isPresent()) {
				List<SubtitleFile> subtitleFiles = getClient()
						.downloadSubtitles(subtitleInfo.get().getSubtitleFileId());
				if (subtitleFiles.size() > 0) {
					subTitle = subtitleFiles.get(0);
				}

//			subtitleFiles.get(0).getContent().getContent().

			}
		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return subTitle;
	}

}
