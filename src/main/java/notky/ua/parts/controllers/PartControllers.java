package notky.ua.parts.controllers;

import notky.ua.parts.constants.Filter;
import notky.ua.parts.constants.PartConst;
import notky.ua.parts.models.Part;
import notky.ua.parts.service.PartService;
import notky.ua.parts.utils.FactoryFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PartControllers {
    private Filter filterSave;
    private String searchSave;

    private PartService partService;

    @RequestMapping(value = "/")
    public String index(HttpServletRequest request,
                        Model model) {
        List<Part> list;
        int page = parseInt(request.getParameter(PartConst.PAGE));
        if(page < 1){
            setSaveAttribute(request);
        }

        // Выбор отображаемого списка учитывая фильтр и поиск
        list = getParts();

        // Разбиение на страници
        int pageCurrent = setNumbersToPages(request, model, list.size());
        List<Part> parts = getListOfCurrentPage(list, pageCurrent);

        model.addAttribute(PartConst.ALL_PARTS, parts);
        model.addAttribute(PartConst.TOTAL, getAvailableComputers());
        return "index";
    }

    @RequestMapping(value = "/edit")
    public String edit(HttpServletRequest request,
                       Model model){
        String name = "";
        boolean necessary = false;
        String quantity = "";
        String error = "";
        String url = PartConst.URL_SAVE;

        // Проверка на сохранение или обновление данных
        String idTemp = request.getParameter(PartConst.ID);
        int id = parseInt(idTemp);
        if(id > 0){
            Part part = partService.getOne(id);
            name = part.getName();
            necessary = part.isNecessary();
            quantity = String.valueOf(part.getQuantity());
            url = PartConst.URL_UPDATE + idTemp;
        }

        // Проверка наличие сообщения об ошибке
        String errorTemp = request.getParameter(PartConst.ERROR);
        if(errorTemp != null && errorTemp.length() > 0){
            name = request.getParameter(PartConst.NAME);
            String necessaryTemp = request.getParameter(PartConst.NECESSARY);
            necessary = necessaryTemp != null && necessaryTemp.equals(PartConst.ON);
            quantity = request.getParameter(PartConst.QUANTITY);
            error = errorTemp;
        }

        // добвление данных на страницу
        model.addAttribute(PartConst.NAME, name);
        model.addAttribute(PartConst.NECESSARY, necessary);
        model.addAttribute(PartConst.QUANTITY, quantity);
        model.addAttribute(PartConst.ERROR, error);
        model.addAttribute(PartConst.URL, url);

        return "pages/editor";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/save")
    public ModelAndView save(HttpServletRequest request,
                             ModelMap modelMap){
        // выбераем атрибуты с запроса
        String necessaryTemp = request.getParameter(PartConst.NECESSARY);
        boolean necessary = necessaryTemp != null && necessaryTemp.equals(PartConst.ON);
        String name = request.getParameter(PartConst.NAME);
        String quantityTemp = request.getParameter(PartConst.QUANTITY);
        int quantity = parseInt(quantityTemp);

        // проверка на коректность данных
        if(name == null || name.length() == 0 || quantity < 0){
            setErrorData(modelMap, name, necessary, quantityTemp);
            return new ModelAndView("redirect:/edit", modelMap);
        }

        // Создание обьекта
        Part part = new Part();
        part.setName(name);
        part.setNecessary(necessary);
        part.setQuantity(quantity);
        partService.save(part);

        return new ModelAndView("redirect:/");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/update")
    public ModelAndView update(HttpServletRequest request,
                         ModelMap modelMap){
        // выбераем атрибуты с запроса
        String idTemp = request.getParameter(PartConst.ID);
        int id = parseInt(idTemp);
        String necessaryTemp = request.getParameter(PartConst.NECESSARY);
        boolean necessary = necessaryTemp != null && necessaryTemp.equals(PartConst.ON);
        String name = request.getParameter(PartConst.NAME);
        String quantityTemp = request.getParameter(PartConst.QUANTITY);
        int quantity = parseInt(quantityTemp);

        // проверка на коректность данных
        if(name == null || name.length() == 0 || quantity < 0){
            setErrorData(modelMap, name, necessary, quantityTemp);
            return new ModelAndView("redirect:/edit?id=" + idTemp, modelMap);
        }

        // обновление обьекта
        Part part = partService.getOne(id);
        part.setName(name);
        part.setNecessary(necessary);
        part.setQuantity(quantity);
        partService.update(part);

        return new ModelAndView("redirect:/");
    }

    @RequestMapping(value = "/delete")
    public String delete(@RequestParam int id){
        partService.delete(id);
        return "redirect:/";
    }

    @Autowired
    public void setPartService(PartService partService) {
        this.partService = partService;
    }

    private int parseInt(String value){
        try{
            return Integer.parseInt(value);
        } catch (Exception e){
            return -1;
        }
    }

    private int getAvailableComputers(){
        List<Part> parts = partService.findOnlyNecessary();
        int count;
        if(parts != null && parts.size() > 0){
            count = parts.get(0).getQuantity();
            for(Part part : parts){
                count = count < part.getQuantity() ? count : part.getQuantity();
            }
        } else {
            count = 0;
        }
        return count;
    }

    private void setSaveAttribute(HttpServletRequest request){
        searchSave = request.getParameter(PartConst.SEARCH);
        String filterTemp = request.getParameter(PartConst.FILTER);
        filterSave = FactoryFilter.getFilter(filterTemp);
    }

    private List<Part> getParts(){
        if(searchSave != null && searchSave.length() > 0){
            return partService.findByContaining(searchSave);
        }
        if(filterSave != null){
            return getListFilter(filterSave);
        }

        return partService.findAll();
    }

    private List<Part> getListFilter(Filter filter){
        switch (filter){
            case All:
                return partService.findAll();
            case NECESSARY:
                return partService.findOnlyNecessary();
            case OTHER:
                return partService.findOther();
        }
        return partService.findAll();
    }

    private int setNumbersToPages(HttpServletRequest request, Model model, int size){
        int pageFirst;
        int pageBack;
        int pageCurrent;
        int pageNext;
        int pageLast;

        int page = parseInt(request.getParameter(PartConst.PAGE));
        int numberPages = size / 10 + 1;
        pageFirst = 1;
        pageLast = numberPages;
        if(page > 0){
            pageCurrent = page;
            pageBack = page - 1 > 0 ? page - 1 : 0;
            pageNext = page + 1 <= numberPages ? page + 1 : 0;
        } else {
            pageCurrent = 1;
            pageBack = 0;
            pageNext = pageCurrent + 1 <= numberPages ? pageCurrent + 1 : 0;
        }
        model.addAttribute(PartConst.PAGE_FIRST, pageFirst);
        model.addAttribute(PartConst.PAGE_BACK, pageBack);
        model.addAttribute(PartConst.PAGE_CURRENT, pageCurrent);
        model.addAttribute(PartConst.PAGE_NEXT, pageNext);
        model.addAttribute(PartConst.PAGE_LAST, pageLast);

        return pageCurrent;
    }

    private List<Part> getListOfCurrentPage(List<Part> list, int pageCurrent){
        List<Part> parts = new ArrayList<>();
        int indexFirst = pageCurrent * 10 - 10;
        int indexLast = pageCurrent * 10 - 1;
        for(int i = indexFirst; i < list.size(); i++){
            parts.add(list.get(i));
            if(i == indexLast){
                break;
            }
        }
        return parts;
    }

    private void setErrorData(ModelMap modelMap, String name, boolean necessary, String quantity){
        modelMap.addAttribute(PartConst.ERROR, PartConst.ERROR_VALUE);
        modelMap.addAttribute(PartConst.NAME, name);
        modelMap.addAttribute(PartConst.NECESSARY, necessary);
        modelMap.addAttribute(PartConst.QUANTITY, quantity);
    }
}
