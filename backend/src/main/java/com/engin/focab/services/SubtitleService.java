package com.engin.focab.services;

import java.net.MalformedURLException;

import org.apache.xmlrpc.XmlRpcException;

import com.github.wtekiela.opensub4j.response.SubtitleFile;

public interface SubtitleService {

	SubtitleFile getASubtitleByImdbId(String id) throws XmlRpcException, MalformedURLException;

}
