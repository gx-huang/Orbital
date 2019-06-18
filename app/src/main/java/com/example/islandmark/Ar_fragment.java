package com.example.islandmark;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.*;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class Ar_fragment extends Fragment implements View.OnClickListener {

    private ArFragment arFragment;
    private ModelRenderable volcanoRenderable;
    private AccountFragment.OnFragmentInteractionListener mListener;

    ImageView volcano;

    //for the future when we import more models
    View arrayView[];
    ViewRenderable name_object;

    int selected = 1 ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arFragment = (ArFragment) getFragmentManager().findFragmentById(R.id.sceneform_fragment);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ar, container, false);

        volcano = (ImageView) rootView.findViewById(R.id.volcano);
        setArrayView();
        setClickListener();

        setUpModel();
        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {

                Anchor anchor = hitResult.createAnchor();
                AnchorNode anchorNode = new AnchorNode(anchor);
                anchorNode.setParent(arFragment.getArSceneView().getScene());

                createModel(anchorNode,selected);
            }
        });
        return rootView;
    }

    private void setUpModel() {
        ModelRenderable.builder()
                .setSource(getActivity(),R.raw.volcano)
                .build();
    }

    private void createModel(AnchorNode anchorNode, int selected) {
        if(selected==1){
            TransformableNode volcano = new TransformableNode(arFragment.getTransformationSystem());
            volcano.setParent(anchorNode);
            volcano.setRenderable(volcanoRenderable);
            volcano.select();
        }
    }

    private void setClickListener() {
        for (int i = 0; i < arrayView.length; i++) {
            arrayView[i].setOnClickListener(this);
        }
    }

    private void setArrayView() {
        arrayView = new View[]{
                //add thumbnails here
                volcano
        };
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onClick(View v) {

    }
}
