/*
 * Minio Java Example, (C) 2016 Minio, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.minio.photoapiservice;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.xmlpull.v1.XmlPullParserException;

import io.minio.errors.MinioException;

@Path("/photoservice")
public class PhotoService {
	// Initialize new album service.
	AlbumDao albumDao = new AlbumDao();

	// Define GET method and resource.
	@GET
	@Path("/list")
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Album> listAlbums(@QueryParam(value="bucket") String minioBucket)
			throws InvalidKeyException, NoSuchAlgorithmException, IOException, XmlPullParserException, MinioException {

		// Return list of albums.
		return albumDao.listAlbums(minioBucket);
	}

	@GET
	@Path("/get")
	@Produces({ MediaType.APPLICATION_JSON })
	public Album getPhoto(@QueryParam(value="name") String name)
			throws InvalidKeyException, NoSuchAlgorithmException, IOException, XmlPullParserException, MinioException {
		return albumDao.getPhoto(name);
	}

	@POST
    @Path("/insert")
    @Produces({MediaType.APPLICATION_JSON})
    public Map<String,String> insertBucket(@FormParam(value = "bucketN") @DefaultValue(value = "liu") String bucketName) throws InvalidKeyException,
            NoSuchAlgorithmException, IOException,
            XmlPullParserException, MinioException {

        return albumDao.insertBucket(bucketName);
    }

	/**
	 * 
	 * @param bucketName:bucket的名字
	 * @param objectName:保存文件的名字
	 * @param fileName:上传文件地址
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws XmlPullParserException
	 * @throws MinioException
	 */
	@POST 
	@Path("/upload")
	public Map<String, String> uploadFile(@FormParam(value = "bucketName") String bucketName,@FormParam(value = "objectName") String objectName,@FormParam(value = "fileName") String fileName)
			throws InvalidKeyException, NoSuchAlgorithmException, IOException, XmlPullParserException, MinioException {
		return albumDao.uploadFile(bucketName, objectName, fileName);
	}
}
