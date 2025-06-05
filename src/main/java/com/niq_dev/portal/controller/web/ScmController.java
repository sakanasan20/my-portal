package com.niq_dev.portal.controller.web;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.niq_dev.portal.dto.InventoryItemDto;
import com.niq_dev.portal.dto.OrderDto;
import com.niq_dev.portal.dto.SupplierDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/scm")
@RequiredArgsConstructor
public class ScmController {

	@GetMapping
	public String scm(Model model) {
		model.addAttribute("contentFragment", "fragments/scm/main");
		return "scm";
	}

	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		model.addAttribute("contentFragment", "fragments/scm/dashboard");
		return "scm";
	}

	@GetMapping("/orders")
	public String orders(Model model) {
		model.addAttribute("contentFragment", "fragments/scm/orders");
		List<OrderDto> orders = List.of(
			    new OrderDto("ORD-001", "王小明", LocalDate.of(2025, 6, 1), "處理中"),
			    new OrderDto("ORD-002", "李小美", LocalDate.of(2025, 6, 2), "已出貨"),
			    new OrderDto("ORD-003", "陳志強", LocalDate.of(2025, 6, 3), "已完成"));
		model.addAttribute("orders", orders);
		return "scm";
	}

	@GetMapping("/inventory")
	public String inventory(Model model) {
		model.addAttribute("contentFragment", "fragments/scm/inventory");
		List<InventoryItemDto> inventory = List.of(
				new InventoryItemDto("P-001", "商品A", 100, "件"),
				new InventoryItemDto("P-002", "商品B", 45, "箱"), 
				new InventoryItemDto("P-003", "商品C", 12, "瓶"));
		model.addAttribute("inventory", inventory);
		return "scm";
	}

	@GetMapping("/suppliers")
	public String suppliers(Model model) {
		model.addAttribute("contentFragment", "fragments/scm/suppliers");
		List<SupplierDto> suppliers = List.of(
				new SupplierDto("宏達科技", "張先生", "0912-345-678", true),
				new SupplierDto("佳美企業", "陳小姐", "0987-654-321", false), 
				new SupplierDto("永豐物資", "李先生", "0922-222-333", true));
		model.addAttribute("suppliers", suppliers);
		return "scm";
	}

	@GetMapping("/reports")
	public String reports(Model model) {
		model.addAttribute("contentFragment", "fragments/scm/reports");
		model.addAttribute("months", List.of("1月", "2月", "3月", "4月", "5月", "6月"));
		model.addAttribute("orderCounts", List.of(12, 19, 3, 5, 2, 9));
		model.addAttribute("inventoryNames", List.of("商品A", "商品B", "商品C"));
		model.addAttribute("inventoryValues", List.of(30, 50, 20));
		return "scm";
	}
}
