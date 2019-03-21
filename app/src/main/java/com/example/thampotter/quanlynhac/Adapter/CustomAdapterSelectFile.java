package com.example.thampotter.quanlynhac.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.thampotter.quanlynhac.Interface.OnClickedListener;
import com.example.thampotter.quanlynhac.R;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class CustomAdapterSelectFile extends RecyclerView.Adapter<CustomAdapterSelectFile.RecycleViewHolder> {
    private final String TAG = CustomAdapterSelectFile.class.getSimpleName();

    private OnClickedListener onClickedListener;
    private Context context;
    private List<File> listFile;

    public CustomAdapterSelectFile(OnClickedListener onClickedListener, Context context, List<File> listFile) {
        this.onClickedListener = onClickedListener;
        this.context = context;
        this.listFile = listFile;
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFileType;
        TextView txtFileName;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFileType = itemView.findViewById(R.id.img_file_type);
            txtFileName = itemView.findViewById(R.id.txt_file_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    File file = new File(listFile.get(getLayoutPosition()).getPath());
                    if (!file.isHidden()) {
                        // Nếu đường dẫn là thư mục
                        if (file.isDirectory()) {
                            String nameFolder = listFile.get(getLayoutPosition()).getName();
                            Log.d(TAG, "onClick: " + nameFolder);
                            listFile.clear(); // Xóa các thư mục và file ở đường dẫn cũ
                            listFile.addAll(Arrays.asList(file.listFiles()));
                            /* Nếu không có lệnh này thì đường dẫn bộ nhớ chính sẽ là /storage/0
                            mà đường dẫn bộ nhớ chính luôn là /storage/emulated/0 */
                            if (nameFolder.equalsIgnoreCase("0")) {
                                nameFolder ="emulated/0";
                            }
                            // Gọi callback về Activity chủ quản (SelectFileActivity) và trả về kết quả
                            onClickedListener.onFolderClicked(nameFolder);
                        } else {
                            if (file.getPath().endsWith("mp3")) {
                                onClickedListener.onMp3FileClicked(file.getPath());
                            } else {
                                Toast.makeText(context, "File is not formatted correctly!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        // gán giao diện cho 1 phần tử của RecyclerView
        View itemView = layoutInflater.inflate(R.layout.item_file, viewGroup, false);
        return new RecycleViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return listFile.size();
    }

    @Override
    public void onBindViewHolder(@NonNull RecycleViewHolder recycleViewHolder, int i) {
        // Đang không ở thư mục root
        if (!isRoot()) {
            if (listFile.get(i).isDirectory()) {
                if(android.os.Build.VERSION.SDK_INT >= 21){
                    recycleViewHolder.imgFileType.setImageDrawable(context.getDrawable(R.drawable.ic_folder));
                } else {
                    recycleViewHolder.imgFileType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_folder));
                }
            } else {
                if(android.os.Build.VERSION.SDK_INT >= 21){
                    recycleViewHolder.imgFileType.setImageDrawable(context.getDrawable(R.drawable.ic_file));
                } else {
                    recycleViewHolder.imgFileType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_file));
                }
            }
            //Set tên cho nó
            recycleViewHolder.txtFileName.setText(listFile.get(i).getName());
        } else {
            // Nếu thư mục có tên là "0" thì là bộ nhớ máy
            if (listFile.get(i).getName().equalsIgnoreCase("0")) {
                if(android.os.Build.VERSION.SDK_INT >= 21){
                    recycleViewHolder.imgFileType.setImageDrawable(context.getDrawable(R.drawable.ic_internal_storage));
                } else {
                    recycleViewHolder.imgFileType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_internal_storage));
                }
                recycleViewHolder.txtFileName.setText("Internal Storage");
            } else {
                if(android.os.Build.VERSION.SDK_INT >= 21){
                    recycleViewHolder.imgFileType.setImageDrawable(context.getDrawable(R.drawable.ic_external_storage));
                } else {
                    recycleViewHolder.imgFileType.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_external_storage));
                }
                recycleViewHolder.txtFileName.setText("External Storage");
            }
        }
    }

    private boolean isRoot() {
        //Nếu nó chỉ có 2 file và có thư mục tên "0" thì nó là thư mục root
        if (listFile.size() <= 2) {
            for (File file: listFile) {
                if (file.getName().equalsIgnoreCase("0")) {
                    return true;
                }
            }
        }

        return false;
    }
}
