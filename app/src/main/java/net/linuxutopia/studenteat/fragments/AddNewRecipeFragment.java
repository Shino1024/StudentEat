package net.linuxutopia.studenteat.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import net.linuxutopia.studenteat.R;
import net.linuxutopia.studenteat.models.Difficulty;
import net.linuxutopia.studenteat.models.DishCategory;
import net.linuxutopia.studenteat.models.IngredientModel;
import net.linuxutopia.studenteat.models.MeasureType;
import net.linuxutopia.studenteat.models.RecipeDetailsModel;
import net.linuxutopia.studenteat.models.StepModel;
import net.linuxutopia.studenteat.utils.AppCompatActivityHelper;

import java.io.File;
import java.util.ArrayList;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import pl.aprilapps.easyphotopicker.EasyImage;

public class AddNewRecipeFragment extends Fragment {

    private LinearLayout newRecipeLayoutContainer;

    private File photoFile;

    private ImageView photoView;
    private TextView photoHint;
    private EditText nameView;
    private EditText descriptionView;
    private TextView difficultySpinnerLabel;
    private Spinner difficultySpinner;
    private TextView dishCategorySpinnerLabel;
    private Spinner dishCategorySpinner;
    private TextView sizeView;

    private ViewGroup ingredientViewGroup;
    private ArrayList<View> ingredientViews = new ArrayList<>();
    private Button newIngredientButton;
    private ViewGroup stepViewGroup;
    private ArrayList<View> stepViews = new ArrayList<>();
    private Button newStepButton;
    private Button submitButton;

    private DisplayMetrics displayMetrics;

    private boolean photoLoaded = false;
    private AlertDialog photoPickerAlertDialog;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private Uri downloadUri;

    public static final int REQUEST_CODE_OPEN_CAMERA = 0x4E;
    public static final int REQUEST_CODE_OPEN_GALLERY = 0x47;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        View inflatedView = inflater.inflate(
                R.layout.new_recipe,
                container,
                false
        );

        AppCompatActivityHelper.setBackButtonAndTitle(getActivity(),
                true,
                R.string.new_recipe_action_bar_title);

        displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        EasyImage.configuration(getActivity())
                .setAllowMultiplePickInGallery(false);

        newRecipeLayoutContainer =
                inflatedView.findViewById(R.id.new_recipe_container);
        ScrollView.LayoutParams layoutParams =
                (ScrollView.LayoutParams) newRecipeLayoutContainer.getLayoutParams();
        int calculatedSideMarginSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                displayMetrics.widthPixels * 0.006f,
                displayMetrics);
        int calculatedTopMarginSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                displayMetrics.heightPixels * 0.006f,
                displayMetrics);
        layoutParams.setMargins(
                calculatedSideMarginSize,
                calculatedTopMarginSize,
                calculatedSideMarginSize,
                calculatedTopMarginSize
        );
        newRecipeLayoutContainer.setLayoutParams(layoutParams);

        photoView = inflatedView.findViewById(R.id.new_recipe_photo);
        photoView.requestLayout();
        photoView.getLayoutParams().height = (int) (displayMetrics.heightPixels * 0.3f);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayDialogWithResult();
            }
        });

        photoHint = inflatedView.findViewById(R.id.new_recipe_photo_hint_text);

        nameView = inflatedView.findViewById(R.id.new_recipe_name_edit);

        descriptionView = inflatedView.findViewById(R.id.new_recipe_description_edit);

        difficultySpinnerLabel = inflatedView.findViewById(R.id.new_recipe_difficulty_spinner_label);
        difficultySpinnerLabel.setText(getResources().getText(
                R.string.new_recipe_difficulty_spinner_hint));

        difficultySpinner = inflatedView.findViewById(R.id.new_recipe_difficulty_spinner);
        fillUpDifficultySpinner();

        dishCategorySpinnerLabel = inflatedView.findViewById(R.id.new_recipe_category_spinner_label);
        dishCategorySpinnerLabel.setText(getResources().getText(
                R.string.new_recipe_category_spinner_hint));
        dishCategorySpinner = inflatedView.findViewById(R.id.new_recipe_category_spinner);
        fillUpDishCategorySpinner();

        sizeView = inflatedView.findViewById(R.id.new_recipe_size_edit);

        ingredientViewGroup = inflatedView.findViewById(R.id.new_recipe_ingredients_list);

        newIngredientButton = inflatedView.findViewById(R.id.new_recipe_new_ingredient_button);
        newIngredientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewIngredient();
            }
        });

        stepViewGroup = inflatedView.findViewById(R.id.new_recipe_steps_list);

        newStepButton = inflatedView.findViewById(R.id.new_recipe_new_step_button);
        newStepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewStep();
            }
        });

        submitButton = inflatedView.findViewById(R.id.new_recipe_submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (
                        !photoLoaded
                        || nameView.getText().toString().trim().matches("")
                        || descriptionView.getText().toString().trim().matches("")
                        || incorrectIngredientsInput()
                        || incorrectStepsInput()
                        ) {
                    AppCompatActivityHelper.displayErrorInToast(
                            (AppCompatActivity) getActivity(),
                            getString(R.string.new_recipe_submit_error)
                    );
                    return;
                }

                DatabaseReference recipeReference = database.getReference("recipes");
                final String pushedKey = recipeReference.push().getKey();

                final UploadDialogFragment uploadDialogFragment = new UploadDialogFragment();
                uploadDialogFragment.show(((AppCompatActivity) getActivity())
                                .getSupportFragmentManager(),
                        "UPLOAD_DIALOG");
                uploadDialogFragment.setCancelable(false);

                StorageReference photosReference = FirebaseStorage
                        .getInstance()
                        .getReference("photos");
                UploadTask uploadTask = photosReference.child(pushedKey)
                        .putFile(Uri.parse(photoFile.toURI().toString()));
                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                uploadDialogFragment.dismiss();
                                downloadUri = taskSnapshot.getDownloadUrl();
                                uploadRecipeDetails(pushedKey);
                            }
                        })
                        .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                MaterialProgressBar progressBar = uploadDialogFragment.getProgressBar();
                                progressBar.setProgress((int) (100 *
                                        taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount()
                                ));
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                uploadDialogFragment.dismiss();
                                AppCompatActivityHelper.displayErrorInToast(
                                        (AppCompatActivity) getActivity(),
                                        e.getLocalizedMessage()
                                );
                            }
                        });
            }
        });

        return inflatedView;
    }

    private void uploadRecipeDetails(String pushedKey) {
        DatabaseReference recipeReference = database.getReference("recipes");
        RecipeDetailsModel recipeDetailsModel = new RecipeDetailsModel();
        recipeDetailsModel.setId(pushedKey);
        recipeDetailsModel.setDownloadLink(downloadUri.toString());
        recipeDetailsModel.setName(nameView.getText().toString());
        recipeDetailsModel.setAuthor(auth.getCurrentUser() != null
                ? auth.getCurrentUser().getDisplayName()
                : "Anonymous");
        recipeDetailsModel.setAuthorId(auth.getUid());
        recipeDetailsModel.setDescription(descriptionView.getText().toString());
        recipeDetailsModel.setDifficulty(
                Difficulty.values()[difficultySpinner.getSelectedItemPosition()]
        );
        recipeDetailsModel.setDishCategory(
                DishCategory.values()[dishCategorySpinner.getSelectedItemPosition()]
        );

        ArrayList<IngredientModel> ingredients = new ArrayList<>();
        double price = 0.0d;
        for (View ingredientView : ingredientViews) {
            EditText newIngredientNameView =
                    ingredientView.findViewById(R.id.new_ingredient_name);
            EditText newIngredientAmountView =
                    ingredientView.findViewById(R.id.new_ingredient_amount);
            Spinner newIngredientMeasureTypeView =
                    ingredientView.findViewById(R.id.new_ingredient_measure_type);
            EditText newIngredientCostView =
                    ingredientView.findViewById(R.id.new_ingredient_cost);
            IngredientModel ingredient = new IngredientModel();
            ingredient.setName(newIngredientNameView.getText().toString());
            ingredient.setAmount(Double.parseDouble(newIngredientAmountView.getText().toString()));
            ingredient.setMeasureType(
                    MeasureType.values()[newIngredientMeasureTypeView.getSelectedItemPosition()]);
            price += Double.parseDouble(newIngredientCostView.getText().toString());
            ingredient.setCost(Double.parseDouble(newIngredientCostView.getText().toString()));
            ingredients.add(ingredient);
        }
        recipeDetailsModel.setIngredients(ingredients);
        recipeDetailsModel.setPrice(price);

        ArrayList<StepModel> steps = new ArrayList<>();
        int minutes = 0;
        for (View stepView : stepViews) {
            EditText newStepDescriptionView =
                    stepView.findViewById(R.id.new_step_description);
            EditText newStepMinutesView =
                    stepView.findViewById(R.id.new_step_minutes);
            StepModel step = new StepModel();
            step.setDescription(newStepDescriptionView.getText().toString().trim());
            minutes += Integer.parseInt(newStepMinutesView.getText().toString());
            step.setMinutes(Integer.parseInt(newStepMinutesView.getText().toString()));
            steps.add(step);
        }
        recipeDetailsModel.setSteps(steps);
        recipeDetailsModel.setMinutes(minutes);

        recipeDetailsModel.setSize(Integer.parseInt(sizeView.getText().toString()));

        Toast.makeText(getActivity(), "parsed!", Toast.LENGTH_SHORT).show();

        recipeReference
                .child(pushedKey)
                .setValue(recipeDetailsModel)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(
                                getActivity(),
                                R.string.new_recipe_on_complete_message,
                                Toast.LENGTH_SHORT
                        ).show();
                        getFragmentManager().popBackStack();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        AppCompatActivityHelper.displayErrorInToast(
                                (AppCompatActivity) getActivity(),
                                e.getMessage()
                        );
                    }
                });

//        DatabaseReference ratingsReference = database.getReference("ratings");
//        ratingsReference.child(recipeDetailsModel.getId())
//        DatabaseReference cookedReference = database.getReference("cooked");
//        DatabaseReference favoriteReference = database.getReference("favorite");
    }

    private boolean incorrectIngredientsInput() {
        if (ingredientViews.size() == 0) {
            return true;
        }

        for (View ingredientView : ingredientViews) {
            EditText ingredientNameView = ingredientView.findViewById(R.id.new_ingredient_name);
            EditText ingredientCostView = ingredientView.findViewById(R.id.new_ingredient_cost);
            EditText ingredientAmountView = ingredientView.findViewById(R.id.new_ingredient_amount);
            if (ingredientNameView.getText().toString().trim().matches("")
                    || ingredientCostView.getText().toString().trim().matches("")
                    || ingredientAmountView.getText().toString().trim().matches("")) {
                return true;
            }
        }

        return false;
    }

    private boolean incorrectStepsInput() {
        if (stepViews.size() == 0) {
            return true;
        }

        for (View stepView : stepViews) {
            EditText stepDescriptionView = stepView.findViewById(R.id.new_step_description);
            EditText stepMinutesView = stepView.findViewById(R.id.new_step_minutes);
            if (stepDescriptionView.getText().toString().trim().matches("")
                    || stepMinutesView.getText().toString().trim().matches("")) {
                return true;
            }
        }

        return false;
    }

    private void displayDialogWithResult() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getResources().getString(R.string.new_recipe_photo_picker_dialog_title));
        builder.setItems(R.array.new_recipe_photo_picker_dialog_options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        if (ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.CAMERA)
                                == PackageManager.PERMISSION_DENIED
                                || ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                == PackageManager.PERMISSION_DENIED) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.CAMERA,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    0);
                        } else {
                            EasyImage.openCamera(getActivity(), REQUEST_CODE_OPEN_CAMERA);
                        }
                        break;

                    case 1:
                        EasyImage.openGallery(getActivity(), REQUEST_CODE_OPEN_GALLERY);
                        break;

                    case 2:
                        break;

                    default:
                        break;
                }
            }
        });

        photoPickerAlertDialog = builder.create();
        photoPickerAlertDialog.show();
    }

    private void fillUpDifficultySpinner() {
        ArrayList<String> difficultyData = new ArrayList<>();
        for (Difficulty difficulty : Difficulty.values()) {
            difficultyData.add(getResources().getString(difficulty.getStringResource()));
        }
        ArrayAdapter<String> difficultyDataAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                difficultyData
        );
        difficultyDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        difficultySpinner.setAdapter(difficultyDataAdapter);
    }

    private void fillUpDishCategorySpinner() {
        ArrayList<String> categoryData = new ArrayList<>();
        for (DishCategory category : DishCategory.values()) {
            categoryData.add(getResources().getString(category.getStringResource()));
        }
        ArrayAdapter<String> categoryDataAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                categoryData
        );
        categoryDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dishCategorySpinner.setAdapter(categoryDataAdapter);
    }

    private void addNewIngredient() {
        final View newIngredientView = LayoutInflater.from(getActivity()).inflate(
                R.layout.new_ingredient_holder,
                ingredientViewGroup,
                false
        );
        Button removeButton = newIngredientView.findViewById(R.id.new_ingredient_remove);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int childIndex = ingredientViews.indexOf(newIngredientView);
                ingredientViewGroup.removeViewAt(childIndex);
                ingredientViews.remove(childIndex);
            }
        });
        Spinner measureTypeSpinner = newIngredientView.findViewById(R.id.new_ingredient_measure_type);
        ArrayList<String> measureTypeData = new ArrayList<>();
        for (MeasureType measureType : MeasureType.values()) {
            measureTypeData.add(getResources().getString(measureType.getStringResource()));
        }
        ArrayAdapter<String> measureTypeDataAdapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_spinner_item,
                measureTypeData
        );
        measureTypeDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        measureTypeSpinner.setAdapter(measureTypeDataAdapter);
        ingredientViews.add(newIngredientView);
        ingredientViewGroup.addView(newIngredientView);
    }

    private void addNewStep() {
        final View newStepView = LayoutInflater.from(getActivity()).inflate(
                R.layout.new_step_holder,
                stepViewGroup,
                false
        );
        Button button = newStepView.findViewById(R.id.new_step_remove);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int childIndex = stepViews.indexOf(newStepView);
                stepViewGroup.removeViewAt(childIndex);
                stepViews.remove(childIndex);
            }
        });
        stepViews.add(newStepView);
        stepViewGroup.addView(newStepView);
    }

    public void displayDishPhotoLoadingError(String error) {
        AppCompatActivityHelper.displayErrorInToast(
                (AppCompatActivity) getActivity(),
                error
        );
    }

    public void onImageLoaded(File photoFile) {
        if (photoFile.exists()) {
            this.photoLoaded = true;
            photoHint.setVisibility(View.GONE);
            this.photoFile = photoFile;

            Bitmap photoBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            photoView.setImageBitmap(photoBitmap);
        } else {
            AppCompatActivityHelper.displayErrorInToast(
                    (AppCompatActivity) getActivity(),
                    getString(R.string.new_recipe_photo_picker_unsuccessful)
            );
        }
    }

    public void openCamera() {
        EasyImage.openCamera(getActivity(), REQUEST_CODE_OPEN_CAMERA);
    }

    public void deniedOpenCameraPermissions() {
        AppCompatActivityHelper.displayErrorInToast(
                (AppCompatActivity) getActivity(),
                getString(R.string.new_recipe_camera_no_permission)
        );
    }
}
