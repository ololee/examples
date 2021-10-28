#include "filesearch.h"
#include "file/SuffixNode.h"
#include "file/SuffixTire.h"
#include "utils/SuffixUtils.h"
#include "utils/FileUtils.h"


void insertSuffixTire(SuffixTire *suffixTirePtr, const char *suffix, int type) {
    suffixTirePtr->insert(suffix, type);
}


/**
 * 在前缀树中查找前缀，找到，返回自定义的类型，否则{未找到}返回0
 * @param suffixTirePtr
 * @param suffix
 * @return
 */
int serachInSuffixTire(SuffixTire *suffixTirePtr, const char *suffix) {
    return suffixTirePtr->search(suffix);
}



/**
 * init suffix tire
 */
NDK_ENTRY(void,
          Java_cn_ololee_filesearch_FileNativeUtils_initSuffixTire(JNIEnv * env, jclass clazz))
    SuffixTire *suffixTirePtr = new SuffixTire();
    jfieldID suffixTireFid = env->GetStaticFieldID(clazz, "suffixTirePtr", "J");
    env->SetStaticLongField(clazz, suffixTireFid, reinterpret_cast<jlong>(suffixTirePtr));
NDK_END

/**
 * 前缀树插入操作
 */
NDK_ENTRY(void, Java_cn_ololee_filesearch_FileNativeUtils_insertSuffixTire(JNIEnv * env,
                                                                           jclass clazz, jstring suffix,
        jint type))
    jfieldID suffixTireFid = env->GetStaticFieldID(clazz, "suffixTirePtr", "J");
    SuffixTire *suffixTirePtr = reinterpret_cast<SuffixTire *>(env->GetStaticLongField(clazz,
                                                                                       suffixTireFid));
    const char *constsuffixChars = env->GetStringUTFChars(suffix, NULL);
    insertSuffixTire(suffixTirePtr, constsuffixChars, type);
NDK_END

/**
 * 插入前缀树的一类
 */
NDK_ENTRY(void, Java_cn_ololee_filesearch_FileNativeUtils_insertSuffixTires(JNIEnv * env,
                                                                            jclass clazz, jobject suffixs,
        jint type))
    jfieldID suffixTireFid = env->GetStaticFieldID(clazz, "suffixTirePtr", "J");
    SuffixTire *suffixTirePtr = reinterpret_cast<SuffixTire *>(env->GetStaticLongField(clazz,
                                                                                       suffixTireFid));
    jclass listClazz;
    jmethodID listSizeMid, listGetMid;
    FIND_CLASS(listClazz, "java/util/List")
    GET_METHOD_ID(listSizeMid, listClazz, "size", "()I")
    GET_METHOD_ID(listGetMid, listClazz, "get", "(I)Ljava/lang/Object;")
    int suffixSize = env->CallIntMethod(suffixs, listSizeMid);
    for (int i = 0; i < suffixSize; ++i) {
        jstring suffixjstr = reinterpret_cast<jstring>(env->CallObjectMethod(suffixs, listGetMid,
                                                                             i));
        const char *constsuffixChars = env->GetStringUTFChars(suffixjstr, NULL);
        insertSuffixTire(suffixTirePtr, constsuffixChars, type);
    }
NDK_END

/**
 * 在前缀树中查找前缀的类型
 */
NDK_ENTRY(jint, Java_cn_ololee_filesearch_FileNativeUtils_getSuffixType(JNIEnv * env,
                                                                        jclass clazz, jstring suffix))
    const char *constsuffixChars = env->GetStringUTFChars(suffix, NULL);
    jfieldID suffixTireFid = env->GetStaticFieldID(clazz, "suffixTirePtr", "J");
    SuffixTire *suffixTirePtr = reinterpret_cast<SuffixTire *>(env->GetStaticLongField(clazz,
                                                                                       suffixTireFid));
    return serachInSuffixTire(suffixTirePtr, constsuffixChars);
NDK_END



/**
 * 从路径或者名字中获得后缀
 */
NDK_ENTRY(jstring, Java_cn_ololee_filesearch_FileNativeUtils_getSuffix(JNIEnv * env,
                                                                       jclass clazz, jstring path_or_name))
    const char *path = env->GetStringUTFChars(path_or_name, NULL);
    const char *suffix = getSuffixFromPath(path);
    return env->NewStringUTF(suffix);
NDK_END



NDK_ENTRY(jobject,
          Java_cn_ololee_filesearch_FileNativeUtils_getFileInfosInPath(JNIEnv * env, jclass clazz,
                  jstring jpath,
                  jboolean single_folder))
    const char *path = env->GetStringUTFChars(jpath, NULL);
    jfieldID suffixTireFid = env->GetStaticFieldID(clazz, "suffixTirePtr", "J");
    SuffixTire *suffixTirePtr = reinterpret_cast<SuffixTire *>(env->GetStaticLongField(clazz,
                                                                                       suffixTireFid));
    if (single_folder) {
//        list<FileInfo> fileInfoList=getFilesInDir(suffixTirePtr,path);
    } else {

    }
    return NULL;
NDK_END


void callback(list<FileInfo> fileInfoList, ParamsPtr params) {
    JNIEnv *env = params->env;
    jobject arrayList = env->NewObject(params->arrayListClazz, params->arrayListConstructorMid,
                                       fileInfoList.size());
    for (FileInfo fileInfo:fileInfoList) {
        jstring jpath=env->NewStringUTF(fileInfo.getPath());
        jstring jname=env->NewStringUTF(fileInfo.getName());
        jobject javaFileInfo = env->NewObject(params->fileInfoClazz,
                                              params->fileInfoConstructorMid,
                                              jpath,
                                              jname,
                                              fileInfo.getType());
        env->CallBooleanMethod(arrayList,params->arrayListAddMid,javaFileInfo);
//        env->ReleaseStringUTFChars(jpath,fileInfo.getPath());
    }
    env->CallVoidMethod(params->fileQueryCallback, params->updateMid, arrayList);
}

NDK_ENTRY(void, Java_cn_ololee_filesearch_FileNativeUtils_getFileInfosWithCallback
        (JNIEnv * env,
         jclass clazz,
        jstring jpath,
        jboolean single_folder,
        jint paged_size,
        jobject file_query_callback))
    const char *path = env->GetStringUTFChars(jpath, NULL);
    jfieldID suffixTireFid = env->GetStaticFieldID(clazz, "suffixTirePtr", "J");
    SuffixTire *suffixTirePtr = reinterpret_cast<SuffixTire *>(env->GetStaticLongField(clazz,
                                                                                       suffixTireFid));
    jclass fileQueryCallbackClazz = env->GetObjectClass(file_query_callback);
    jclass arrayListClazz;//ArrayList.class
    jclass fileInfoClazz;//FileInfo.class
    jmethodID fileInfoConstructorMid;//FileInfo(String path, String name, int type)
    jmethodID arrayListConstructorMid;//ArrayList(int initialCapacity);
    jmethodID updateMid;// void update(List<FileInfo> fileInfoList);
    jmethodID addMid;//void add(E e);
    FIND_CLASS(arrayListClazz, "java/util/ArrayList")
    FIND_CLASS(fileInfoClazz, FILE_INFO_CLAZZ)
    GET_METHOD_ID(fileInfoConstructorMid, fileInfoClazz, "<init>",
                  "(Ljava/lang/String;Ljava/lang/String;I)V")
    GET_METHOD_ID(arrayListConstructorMid, arrayListClazz, "<init>", "(I)V")
    GET_METHOD_ID(updateMid, fileQueryCallbackClazz, "update", "(Ljava/util/List;)V")
    GET_METHOD_ID(addMid, arrayListClazz, "add", "(Ljava/lang/Object;)Z")
    Params params = {
            .env=env,
            .fileQueryCallback=file_query_callback,
            .arrayListClazz=arrayListClazz,
            .arrayListConstructorMid=arrayListConstructorMid,
            .arrayListAddMid=addMid,
            .fileInfoClazz=fileInfoClazz,
            .fileInfoConstructorMid=fileInfoConstructorMid,
            .updateMid=updateMid
    };
    ParamsPtr paramsPtr = &params;
    if (single_folder) {
        LOGE("path:%s",path)
        getFilesInDirWithCallback(suffixTirePtr, path, paged_size, paramsPtr, callback);
    } else {

    }
NDK_END


