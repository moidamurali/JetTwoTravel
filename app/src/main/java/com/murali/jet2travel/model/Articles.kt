/**
 * Created by Murali Mohan on 13/10/2020.
 */

data class Articles (
	val id : Int,
	val createdAt : String,
	val content : String,
	val comments : Int,
	val likes : Int,
	val media : List<Media>,
	val user : List<User>
)