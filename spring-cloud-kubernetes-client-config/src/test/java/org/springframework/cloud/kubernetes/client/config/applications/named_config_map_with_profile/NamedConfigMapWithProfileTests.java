/*
 * Copyright 2013-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.kubernetes.client.config.applications.named_config_map_with_profile;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * The stub data for this test is in :
 * {@link org.springframework.cloud.kubernetes.client.config.boostrap.stubs.NamedConfigMapWithProfileConfigurationStub}
 *
 * @author wind57
 */
abstract class NamedConfigMapWithProfileTests {

	@Autowired
	private WebTestClient webClient;

	@AfterEach
	public void afterEach() {
		WireMock.reset();
	}

	@AfterAll
	static void afterAll() {
		WireMock.shutdownServer();
	}

	/**
	 * <pre>
	 *   'spring.cloud.kubernetes.config.useNameAsPrefix=true'
	 *   'spring.cloud.kubernetes.config.sources[0].useNameAsPrefix=false'
	 *   'spring.cloud.kubernetes.config.sources[0].includeProfileSpecificSources=true'
	 * 	 ("one.property", "one-from-k8s")
	 *
	 * 	 As such: @ConfigurationProperties("one"), value is overridden by the one that we read from
	 * 	 the profile based source.
	 * </pre>
	 */
	@Test
	void testOne() {
		this.webClient.get().uri("/named-configmap/profile/one").exchange().expectStatus().isOk()
				.expectBody(String.class).value(Matchers.equalTo("one-from-k8s"));
	}

	/**
	 * <pre>
	 *   'spring.cloud.kubernetes.config.useNameAsPrefix=true'
	 *   'spring.cloud.kubernetes.config.sources[1].explicitPrefix=two'
	 *   'spring.cloud.kubernetes.config.sources[1].includeProfileSpecificSources=false'
	 * 	 ("property", "two")
	 *
	 * 	 As such: @ConfigurationProperties("two").
	 *
	 * 	 Even if there is a profile based source, we disabled reading it.
	 * </pre>
	 */
	@Test
	void testTwo() {
		this.webClient.get().uri("/named-configmap/profile/two").exchange().expectStatus().isOk()
				.expectBody(String.class).value(Matchers.equalTo("two"));
	}

	/**
	 * <pre>
	 *   'spring.cloud.kubernetes.config.useNameAsPrefix=true'
	 *   'spring.cloud.kubernetes.config.sources[2].name=configmap-three'
	 *   'spring.cloud.kubernetes.config.sources[1].includeProfileSpecificSources=true'
	 * 	 ("property", "three")
	 *
	 * 	 As such: @ConfigurationProperties(prefix = "config-three"), value is overridden by the one that we read from
	 * 	 the profile based source
	 * </pre>
	 */
	@Test
	void testThree() {
		this.webClient.get().uri("/named-configmap/profile/three").exchange().expectStatus().isOk()
				.expectBody(String.class).value(Matchers.equalTo("three-from-k8s"));
	}

}
