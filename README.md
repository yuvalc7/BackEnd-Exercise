# BackEnd-Exercise - a web service exposing a simple API.

## about the project  
build  service that will be in
charge of hashing blobs of data in a consistent and suitable manner.  
there are 3 endpoint in this project:  
● Hash key registration:
      When registering a new arbitrary “hash key”, a user provides a secret salt numer and a
      hash algorithm name/ID.  =>  for register and store the mapping internally.
● List existing hash keys: => discover existing key algorithem and crated time for each key 
● Hash: Provided with a specific hashing key and secret to hash, using stored salt & appropriate hash algorithm store in map where hash key => return a base64 string of hashed secret.


Current API usage example (You do not have to stick with it):  
REGISTER:
curl -XPOST localhost:8080/register/sparkB_generic_hash \
-H "Content-Type:application/json" \
-d '{"salt":1234,"algorithm":"SHA-384"}'  
LIST:
curl localhost:8080/keys  
HASH:
curl -XPOST localhost:8080/hash/sparkB_generic_hash -d 'secret'  

*Exception & edge cases:
 - befor user try to registerd i validate algorithm provide, if algorithm Invalid the regisration faild means this regestration not stored 
 - user can't register again with same key already in use.  
 - user can't hash hashing key provide that not exist in the map 
 
