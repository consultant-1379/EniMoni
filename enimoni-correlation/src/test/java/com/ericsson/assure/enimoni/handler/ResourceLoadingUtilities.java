package com.ericsson.assure.enimoni.handler;

/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2012
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/

import java.io.InputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResourceLoadingUtilities {

    private static final Logger logger = LoggerFactory.getLogger(ResourceLoadingUtilities.class);

    private static final String FILE_PREFIX = "file:";

    public static URL getURLForResourceOnClassPath(final String resource) {
        return new ResourceLoadingUtilities().getURLForResourceFromClassPath(resource);
    }

    URL getURLForResourceFromClassPath(final String resource) {
        URL urlForResource = ClassLoader.getSystemClassLoader().getResource(resource);
        logger.debug("on system class path, for " + resource + " found " + urlForResource);
        if (urlForResource == null) {
            urlForResource = ResourceLoadingUtilities.class.getClassLoader().getResource(resource);
            logger.debug("on class's class path, for " + resource + " found " + urlForResource);
        }
        return urlForResource;

    }

    public static InputStream getInputStreamForResourceOnClassPath(final String resource) {
        final InputStream inputStream = ClassLoader.getSystemClassLoader().getResourceAsStream(resource);
        if (inputStream == null) {
            return ResourceLoadingUtilities.class.getClassLoader().getResourceAsStream(resource);
        }
        return inputStream;

    }

    public String getPathForResourceOnClassPath(final String resource) {
        final String path = getPathOfResourceOnClassPath(resource);
        return trimFilePrefixFromPathIfRequired(path);
    }

    private String trimFilePrefixFromPathIfRequired(final String path) {
        if (path.startsWith(FILE_PREFIX)) {
            return path.substring(5, path.length());
        }
        return path;
    }

    String getPathOfResourceOnClassPath(final String resource) {
        return getURLForResourceFromClassPath(resource).getPath();
    }

}