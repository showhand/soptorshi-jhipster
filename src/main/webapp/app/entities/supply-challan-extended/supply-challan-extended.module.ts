import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplyChallanDeleteDialogExtendedComponent,
    SupplyChallanDeletePopupExtendedComponent,
    SupplyChallanDetailExtendedComponent,
    SupplyChallanExtendedComponent,
    supplyChallanExtendedRoute,
    supplyChallanPopupExtendedRoute,
    SupplyChallanUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...supplyChallanExtendedRoute, ...supplyChallanPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplyChallanExtendedComponent,
        SupplyChallanDetailExtendedComponent,
        SupplyChallanUpdateExtendedComponent,
        SupplyChallanDeleteDialogExtendedComponent,
        SupplyChallanDeletePopupExtendedComponent
    ],
    entryComponents: [
        SupplyChallanExtendedComponent,
        SupplyChallanUpdateExtendedComponent,
        SupplyChallanDeleteDialogExtendedComponent,
        SupplyChallanDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSupplyChallanExtendedModule {}
