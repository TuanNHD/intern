package com.example.demo.viewModel;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.validation.Validator;

import org.apache.catalina.Store;
import org.zkoss.bind.annotation.BindingParam;
import org.zkoss.bind.annotation.Init;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.bind.validator.AbstractValidator;
import org.zkoss.util.media.Media;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.annotation.Command;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.DelegatingVariableResolver;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.ListModelList;

import com.example.demo.CarService.CarService;
import com.example.demo.CarService.CarServiceImpl;
import com.example.demo.Repository.CarRepository;
import com.example.demo.entity.Car;
import com.example.demo.validator.MakeValidator;

/**
 * Suggest using ZK {@link org.zkoss.zul.ListModel} implementation as a
 * component's model.
 */
@VariableResolver(DelegatingVariableResolver.class)
public class SearchViewModel{

	private String keyword;
	private List<Car> carList = new ListModelList<Car>();
	private Car selectedCar;
	private Car editCar;
	private boolean isOpen;
	private boolean isOpenPopup = false;
	private boolean isOpenImg = false;
	private static Car createCar;
	Store store;
@WireVariable
CarRepository carRepository;
CarService carService;
AbstractValidator makeValidator;
public AbstractValidator getMakeValidator() {
	return  new MakeValidator();
}
@Init
public void init() {
	carService= new CarServiceImpl();
	carRepository.saveAll(carService.listAddCar());
	carList.addAll(carRepository.findAll());
	
}
	public boolean getIsOpenPopup() {
		return isOpenPopup;
	}

	@NotifyChange("isOpenPopup")
	public void setIsOpenPopup(boolean isOpenPopup) {
		this.isOpenPopup = isOpenPopup;
	}

	public boolean getIsOpen() {
		return isOpen;
	}

	public void setIsOpen(boolean isOpen) {
		this.isOpen = isOpen;
	}

	public Car getEditCar() {
		return editCar;
	}

	public void setEditCar(Car editCar) {
		this.editCar = editCar;
	}

																					// class

	public Car getCreateCar() {
		return createCar;
	}

	public void setCreateCar(Car createCar) {
		this.createCar = createCar;
	}

	@Command
	public void addNewCar() throws IOException {
		
		System.out.println("add new ");
		carRepository.save(createCar);
		Executions.sendRedirect("/car");
	}

	public void setCarList(List<Car> carList) {
		this.carList = carList;
	}

	@Command
	public void search() {
		carList.clear();
		carList.addAll(carService.search(keyword,carList));
	}

	@Command
	@NotifyChange({ "selectedCar", "isOpen", "isOpenPopup" })
	public void add() {
		this.isOpenPopup = true;
		this.selectedCar = null;
		this.isOpen = false;
		createCar = new Car();
		System.out.println("add");
	}
	@Command
	@NotifyChange({"isOpenImg" })
	public void showImg() {
		this.isOpenImg = true;
		System.out.println("helloimg  "+this.createCar.getPreview());
	}

	@Command
	@NotifyChange("carList")
	public void edit() {
		System.out.println("edit");
		for (Car car : carList) {
			if (car.getId().equals(selectedCar.getId())) {
				car.setMake(selectedCar.getMake());
				car.setModel(selectedCar.getModel());
				car.setPrice(selectedCar.getPrice());
				car.setDescription(selectedCar.getDescription());
				break;
			}
		}
		carRepository.save(selectedCar);
		;
	}

	@Command
	@NotifyChange({ "selectedCar", "isOpen","carList","createCar"})
	public void delete(@BindingParam("car") Car car) {
		System.out.println("delete");
		carList.remove(car);
		carRepository.delete(car);
		selectedCar=null;
		isOpen = false;
		createCar=null;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getKeyword() {
		return keyword;
	}

	public List<Car> getCarList() {
		return carList;
	}

	@NotifyChange({ "selectedCar", "isOpen", "isOpenPopup" })
	public void setSelectedCar(Car selectedCar) {
		this.selectedCar = selectedCar;
		this.isOpen = selectedCar != null;
		this.isOpenPopup = false;
	}

	public Car getSelectedCar() {
		return selectedCar;
	}

	@Command
	@NotifyChange("isOpenPopup")
	public void addCar() {
		// Logic to add a car
		this.isOpenPopup = true;
	}

	@Command
	@NotifyChange("isOpenPopup")
	public void closePopup() {
		this.isOpenPopup = false;
	}

	@Command
	@NotifyChange("createCar") // Notify only the preview property change
	public void uploadImage() {
		StringBuilder url = new StringBuilder();
	    Fileupload.get(event -> {
	        Media media = event.getMedia();
	        if (media != null) {
	            try {
	                // Your existing image save logic
	                String webAppPath = Executions.getCurrent().getDesktop().getWebApp().getRealPath("/");
	                File imgDir = new File(webAppPath, "img");
	                if (!imgDir.exists()) {
	                    imgDir.mkdirs();
	                }
	                File uploadedFile = new File(imgDir, media.getName());
	                Files.copy(media.getStreamData(), uploadedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

	                // Update the preview property
	                String imagePath = "/img/" + uploadedFile.getName();
	                 url.append(imagePath); // Assuming createCar has a setPreview method
	                 savePreview(imagePath);
	            } catch (IOException e) {
	                e.printStackTrace();
	                Clients.showNotification("Failed to upload image.", "error", null, "top_center", 3000);
	            }
	        }
	    });
	}

	public boolean getIsOpenImg() {
		return isOpenImg;
	}

	public void setIsOpenImg(boolean isOpenImg) {
		this.isOpenImg = isOpenImg;
	}
	public void savePreview(String path) {
		this.createCar.setPreview(path);
	}
	
}
