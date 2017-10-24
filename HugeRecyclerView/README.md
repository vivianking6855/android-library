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

    
# Feature List

- Header,Footer （可以直接搭配系统的RecyclerView使用）
- Endless 上滑刷新 （可以直接搭配系统的RecyclerView使用）
- 下拉刷新 （SwipeRefreshLayout实现下拉刷新和三方库实现下拉刷新）

# 一、 Header,Footer

效果图

   ![](https://i.imgur.com/w6rp5sD.png)

   ![](https://i.imgur.com/fATcYUi.png)
    
Header，Footer使用方法简介如下：（详见sample的 HeaderFooterActivity）

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


# 二、 Endless Footer

效果图

   ![](https://i.imgur.com/jl9baMT.jpg)

   ![](https://i.imgur.com/5jcGgJt.jpg)
    
   ![](https://i.imgur.com/1JwjYs6.jpg)
   
   ![](https://i.imgur.com/kQDYnbC.png)
   
   ![](https://i.imgur.com/Qco3dp7.png)

核心类说明

- EndlessFooterUtils: 依据数据加载的状态改变底层footer的UI显示
- HugeRecyclerOnScrollListener: 滚动监听，可以在这里处理滚动结束，加载数据的动作
- EndlessFooterView：底层footer view，里面有不同状态对应的UI布局

- 上滑基本使用方法简介如下：（详见sample的 EndlessActivity）

mFooterUtil = new EndlessFooterUtils(new EndlessFooterView(this));

Activity中配置HugeRecycler的listener

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

然后依据数据加载的状态调用mFooterUtil的方法设定FooterView显示状态

- 上滑的高级用法：自定义EndlessFooterView实现客制化的UI界面

    可以直接继承EndlessFooterView 或者 BaseEndlessFooterView （详见sample的 CustomerEndlessActivity）

    1. 定义 CustomerEndlessFooterView extends EndlessFooterView. 
     
            @Override
            protected void setContentView(Context context) {
                inflate(context, R.layout.endless_user_footer, this);
            }
    
    2. 自定义三种状态的xml
     
        参见sample
        
        endless_user_footer.xml,
        
        endless_user_footer_loading.xml,
        
        endless_user_footer_error.xml,
        
        endless_user_footer_end.xml
        
        注意endless_user_footer.xml中一定要包含下面的三个id
        
            <ViewStub
                android:id="@+id/loading_viewstub"
                android:layout_width="match_parent"
                android:layout_height="@dimen/endless_footer_h"
                android:layout="@layout/endless_footer_loading" />
        
            <ViewStub
                android:id="@+id/end_viewstub"
                android:layout_width="match_parent"
                android:layout_height="@dimen/endless_footer_h"
                android:layout="@layout/endless_footer_end" />
        
            <ViewStub
                android:id="@+id/error_viewstub"
                android:layout_width="match_parent"
                android:layout_height="@dimen/endless_footer_h"
                android:layout="@layout/endless_footer_error" />

    
    3. mFooterUtil = new EndlessFooterUtils(new CustomerEndlessFooterView(this));


# 三、 下拉刷新

SwipeRefreshLayout实现下拉刷新

    ![](https://i.imgur.com/heGhSkj.jpg)
    


实现直接参看[RecyclerView封装 四](http://vivianking6855.github.io/2017/09/30/RecyclerView-Advance-4/)

# Blog

Huger RecyclerView封装blog

- [RecyclerView封装 一](http://vivianking6855.github.io/2017/09/29/RecyclerView-Advance-1/)
- [RecyclerView封装 二](http://vivianking6855.github.io/2017/09/30/RecyclerView-Advance-2/)
- [RecyclerView封装 三](http://vivianking6855.github.io/2017/09/30/RecyclerView-Advance-3/)
- [RecyclerView封装 四](http://vivianking6855.github.io/2017/09/30/RecyclerView-Advance-4/)
- [RecyclerView封装 五](http://vivianking6855.github.io/2017/10/31/RecyclerView-Advance-5/)
- [RecyclerView封装 六](http://vivianking6855.github.io/2017/11/01/RecyclerView-Advance-6/)
- [RecyclerView封装 七](http://vivianking6855.github.io/2017/11/08/RecyclerView-Advance-7/)
- [RecyclerView封装 八](http://vivianking6855.github.io/2017/11/22/RecyclerView-Advance-8/)


