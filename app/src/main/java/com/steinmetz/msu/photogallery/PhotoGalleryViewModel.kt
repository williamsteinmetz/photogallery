import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.steinmetz.msu.photogallery.PhotoRepository
import com.steinmetz.msu.photogallery.api.GalleryItem
import kotlinx.coroutines.flow.Flow

private const val TAG = "PhotoGalleryViewModel"
private const val ITEMS_PER_PAGE = 10

class PhotoGalleryViewModel : ViewModel() {
    private val photoRepository = PhotoRepository()

    val galleryItems: Flow<PagingData<GalleryItem>> =
        Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE, enablePlaceholders = false),
            pagingSourceFactory = { photoRepository.galleryItemPagingSource() }
        ).flow
            .cachedIn(viewModelScope)
    init {
        Log.d(TAG, "This is the ViewModel loading Correctly.")
    }


}