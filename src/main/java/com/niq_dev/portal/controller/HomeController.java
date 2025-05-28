package com.niq_dev.portal.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
	
    @Value("${oauth2.api-uri}")
    private String apiUri;
	
	@Autowired
	private WebClient webClient;
	
	@Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

	@GetMapping("/")
	public String home(Model model, @AuthenticationPrincipal OidcUser oidcUser) {
		if (oidcUser != null) {
		    List<String> systems = Arrays.asList(oidcUser.getClaimAsString("systems").split(" "));
		    List<String> modules = Arrays.asList(oidcUser.getClaimAsString("modules").split(" "));
		    List<String> features = Arrays.asList(oidcUser.getClaimAsString("features").split(" "));
		    model.addAttribute("systems", systems);
		    model.addAttribute("modules", modules);
		    model.addAttribute("features", features);
		}
		model.addAttribute("user", oidcUser);

	    return "home";
	}
	
	@GetMapping("/getHello")
	public String getHello(RedirectAttributes redirectAttributes, OAuth2AuthenticationToken authentication) {
		OAuth2AuthorizedClient client = null;
		
		if (authentication != null) {
    		client = authorizedClientService.loadAuthorizedClient("portal-client", authentication.getName());
    	}
		
    	if (client != null) {
            String accessToken = client.getAccessToken().getTokenValue();
            
            String result = webClient.get()
                    .uri(apiUri)
                    .header("Authorization", "Bearer " + accessToken)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            redirectAttributes.addFlashAttribute("result", result);
      	}
    	
	    return "redirect:/";
	}
	
}
