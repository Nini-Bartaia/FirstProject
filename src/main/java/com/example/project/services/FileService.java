package com.example.project.services;
import com.example.project.exception.ResourceUploadException;
import com.example.project.props.MinioProperties;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final MinioClient minioClient;
    private final MinioProperties minioProperties;

    public String upload(MultipartFile multipartFile) {

        try {
            createBucket();
        }catch (Exception e){

            throw new ResourceUploadException("Image upload failed" + e.getMessage());
        }

        MultipartFile file = multipartFile;

        if(file.isEmpty() || file.getOriginalFilename()== null){
            throw new ResourceUploadException("Image must have name");
        }

        String filename= generateFileName(file);
        InputStream inputStream;

        try {
            inputStream=file.getInputStream();

        }catch (Exception e){
            throw new ResourceUploadException("Image upload failed" + e.getMessage());

        }
        saveImage(inputStream, filename);
        return filename;

    }
    @SneakyThrows
    private void createBucket(){
        boolean found= minioClient.bucketExists(BucketExistsArgs.builder().bucket(minioProperties.getBucket()).build());
        if(!found){
            minioClient.makeBucket(MakeBucketArgs.builder().bucket(minioProperties.getBucket()).build());
        }

    }

    private String generateFileName(MultipartFile file) {
        String extension = getExtension(file);
        return UUID.randomUUID()+"."+extension;

    }

    public String getExtension(MultipartFile file) {
        return file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
    }

    @SneakyThrows
    private void saveImage(InputStream inputStream, String filename) {
        minioClient.putObject(PutObjectArgs.builder()
                .stream(inputStream, inputStream.available(), -1)
                .bucket(minioProperties.getBucket())
                .object(filename).build());
    }


}
