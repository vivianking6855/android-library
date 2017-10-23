# HugeRecyclerView

It's a huge and smart RecyclerView

Gradle

    // huge recyclerview library
    compile 'com.open:hugerecyclerview:1.0.0'

Maven

    <dependency>
      <groupId>com.open</groupId>
      <artifactId>hugerecyclerview</artifactId>
      <version>1.0.0</version>
      <type>pom</type>
    </dependency>
    
控件引用

    <com.open.hugerecyclerview.HugeRecyclerView
        android:id="@+id/recycler_view"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />

备注：HeaderFooter和Endless Activity也可以直接用系统的RecyclerView
    
# Feature List

- Header,Footer
- Endless 上滑刷新
- 下拉刷新

# Header,Footer

   ![](https://i.imgur.com/w6rp5sD.png)

   ![](https://i.imgur.com/fATcYUi.png)
    
## 使用方法：详见sample的 HeaderFooterActivity

1. 定义HeaderFooterRecyclerAdapter继承BaseRecyclerAdapter，再加两个接口setData和addData

       public class HeaderFooterRecyclerAdapter extends
            BaseRecyclerAdapter<SampleModel, HeaderFooterRecyclerAdapter.ItemViewHolder> {
    
            private Context mContext;
        
            public HeaderFooterRecyclerAdapter(Context context) {
                mContext = context;
            }
        
            public void setData(List<SampleModel> data) {
                mItemList.clear();
                mItemList.addAll(data);
                notifyDataSetChanged();
            }
        
            public void addData(List<SampleModel> data) {
                int len = mItemList.size();
                mItemList.addAll(data);
                notifyItemChanged(len);
            }
        
            @Override
            public ItemViewHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
                return new ItemViewHolder(LayoutInflater.from(mContext)
                        .inflate(R.layout.recycler_item, parent, false));
            }
        
            @Override
            public void onBindItemViewHolder(ItemViewHolder holder, int position) {
                holder.hint.setText(mItemList.get(position).mTitle);
            }
        
            class ItemViewHolder extends RecyclerView.ViewHolder {
                private TextView hint;
        
                ItemViewHolder(View view) {
                    super(view);
                    hint = (TextView) view.findViewById(R.id.tv_content);
                }
            }
        
        }

2. HeaderFooterActivity 加上几句,看星星下面的几句，使用就是这么简单。

       @Override
        protected void initView(Bundle savedInstanceState) {
            setContentView(R.layout.activity_head_footer);
    
            RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            LinearLayoutManager lm = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(lm);
            mAdapter = new HeaderFooterRecyclerAdapter(this);
            mRecyclerView.setAdapter(mAdapter);
    
            // add header and foot for RecyclerView ****
            mAdapter.addHeaderView(getLayoutInflater().inflate(R.layout.recycler_header, mRecyclerView, false));
            mAdapter.addHeaderView(getLayoutInflater().inflate(R.layout.recycler_header, mRecyclerView, false));
            mAdapter.addFooterView(getLayoutInflater().inflate(R.layout.recycler_footer, mRecyclerView, false));
            mAdapter.addFooterView(getLayoutInflater().inflate(R.layout.recycler_footer, mRecyclerView, false));
    
            mHint = (TextView) findViewById(R.id.tv_hint);
        }


# Endless Footer

   ![](https://i.imgur.com/jl9baMT.jpg)

   ![](https://i.imgur.com/5jcGgJt.jpg)
    
   ![](https://i.imgur.com/1JwjYs6.jpg)

## 使用方法：详见sample的 EndlessActivity

- EndlessFooterUtils: 依据数据加载的状态改变底层footer的UI显示
- HugeRecyclerOnScrollListener: 滚动监听，可以在这里处理滚动结束，加载数据的动作
- EndlessFooterView：底层footer view，里面有不同状态对应的UI布局

1. 定义 EndlessFooterView extends BaseEndlessFooterView. 实现三个加载状态的UI的配置和更新：加载中/全部加载完成/加载异常

自定义三种状态的xml：参见sample的endless_footer.xml,endless_footer_loading.xml,endless_footer_error.xml,endless_footer_end.xml

    public class EndlessFooterView extends BaseEndlessFooterView {
        private final static String TAG = "EndlessFooterView";
    
        private TextView mLoadingText;
        private ProgressBar mLoadingProgress;
    
        private Context mContext;
    
        public EndlessFooterView(Context context){
            super(context);
            mContext = context;
            initView();
        }
    
        @Override
        protected void setContentView() {
            inflate(mContext, R.layout.endless_footer, this);
        }
    
        @Override
        protected void setLoadingView() {
            if (mLoadingView == null) {
                ViewStub viewStub = (ViewStub) findViewById(R.id.loading_viewstub);
                mLoadingView = viewStub.inflate();
    
                mLoadingProgress = (ProgressBar) mLoadingView.findViewById(R.id.loading_progress);
                mLoadingText = (TextView) mLoadingView.findViewById(R.id.loading_text);
            }
            mLoadingProgress.setVisibility(View.VISIBLE);
            mLoadingText.setText(R.string.list_footer_loading);
        }
    
        @Override
        protected void setErrorView() {
            if (mNetworkErrorView == null) {
                ViewStub viewStub = (ViewStub) findViewById(R.id.error_viewstub);
                mNetworkErrorView = viewStub.inflate();
            }
        }
    
        @Override
        protected void setEndView() {
            ViewStub viewStub = (ViewStub) findViewById(R.id.end_viewstub);
            mTheEndView = viewStub.inflate();
        }
    }

2. Activity中配置HugeRecycler的listener

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(lm);
        mAdapter = new EndlessRecyclerAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mFooterUtil = new EndlessFooterUtils(new EndlessFooterView(this));
        // set scroll listener
        mHugeOnScrollListener = new HugeRecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                EndlessFooterView.State state = mFooterUtil.getFooterViewState(mRecyclerView);
                // still loading, do nothing
                if (EndlessFooterView.State.Loading == state) {
                    Log.d(TAG, "still loading, now return");
                    return;
                }
                // no more data
                if (mCurrentNum > TOTAL_SIZE) {
                    mFooterUtil.setEnd(EndlessActivity.this, mRecyclerView, PAGE_SIZE);
                    return;
                }

                refreshData();
            }
        };
        // add scroll listener
        mRecyclerView.addOnScrollListener(mHugeOnScrollListener);


# Blog

Huger RecyclerView封装blog

- [RecyclerView封装 一](http://vivianking6855.github.io/2017/09/29/RecyclerView-Advance-1/)
- [RecyclerView封装 二](http://vivianking6855.github.io/2017/09/30/RecyclerView-Advance-2/)
- [RecyclerView封装 三](http://vivianking6855.github.io/2017/09/30/RecyclerView-Advance-3/)
- [RecyclerView封装 四](http://vivianking6855.github.io/2017/09/30/RecyclerView-Advance-4/)
- [RecyclerView封装 五](http://vivianking6855.github.io/2017/09/30/RecyclerView-Advance-5/)


