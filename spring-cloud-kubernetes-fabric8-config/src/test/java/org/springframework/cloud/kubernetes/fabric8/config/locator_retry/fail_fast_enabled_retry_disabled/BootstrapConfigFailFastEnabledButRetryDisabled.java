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

package org.springframework.cloud.kubernetes.fabric8.config.locator_retry.fail_fast_enabled_retry_disabled;

import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.server.mock.EnableKubernetesMockClient;
import io.fabric8.kubernetes.client.server.mock.KubernetesMockServer;
import org.junit.jupiter.api.BeforeAll;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.kubernetes.fabric8.config.Application;

/**
 * we call Fabric8ConfigMapPropertySourceLocator::locate directly, thus no need for
 * bootstrap phase to kick in. As such two flags that might look a bit un-expected:
 * "spring.cloud.kubernetes.config.enabled=false"
 * "spring.cloud.kubernetes.secrets.enabled=false"
 *
 * @author Isik Erhan
 * @author wind57
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
		properties = { "spring.cloud.kubernetes.client.namespace=default",
				"spring.cloud.kubernetes.config.fail-fast=true", "spring.cloud.kubernetes.config.retry.enabled=false",
				"spring.main.cloud-platform=KUBERNETES", "spring.cloud.kubernetes.config.enabled=false",
				"spring.cloud.kubernetes.secrets.enabled=false", "spring.cloud.bootstrap.enabled=true" },
		classes = Application.class)
@EnableKubernetesMockClient
class BootstrapConfigFailFastEnabledButRetryDisabled extends ConfigFailFastEnabledButRetryDisabled {

	private static KubernetesMockServer mockServer;

	private static KubernetesClient mockClient;

	@BeforeAll
	static void setup() {
		setup(mockClient, mockServer);
	}

}
