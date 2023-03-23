package com.puresoftware.bottomnavigationappbar.Weggler.Adapter

//
//class ItemMiniProfileAdapter(
//    itemList : ArrayList<Profile>,
//    private val mainActivity: MainActivity
//) : RecyclerView.Adapter<ItemMiniProfileAdapter.ItemMiniProfileViewHolder>() {
//
//    private lateinit var binding: ItemMiniProfileBinding
//    var itemSet  = itemList
//
//    inner class ItemMiniProfileViewHolder(private val binding:ItemMiniProfileBinding)
//        :RecyclerView.ViewHolder(binding.root) {
//        fun bind() {
//            val profile = itemSet[absoluteAdapterPosition]
//            //뷰 세팅
//            binding.mpBoxUsername.text = profile.name
//            Glide.with(mainActivity)
//                .load(profile.userImage)
//                .into(binding.mpBoxImage)
//
//            //클릭 이벤트
//            binding.root.setOnClickListener {
//
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemMiniProfileViewHolder {
//        binding = ItemMiniProfileBinding.inflate(LayoutInflater.from(mainActivity),parent,false)
//        return ItemMiniProfileViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ItemMiniProfileViewHolder, position: Int) {
//        holder.bind()
//    }
//
//    override fun getItemCount()= itemSet.size
//}