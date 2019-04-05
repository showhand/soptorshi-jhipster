import { NgModule } from '@angular/core';

import { SoptorshiSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [SoptorshiSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [SoptorshiSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class SoptorshiSharedCommonModule {}
