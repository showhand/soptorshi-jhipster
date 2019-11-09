import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplyChallanComponent,
    SupplyChallanDeleteDialogComponent,
    SupplyChallanDeletePopupComponent,
    SupplyChallanDetailComponent,
    supplyChallanPopupRoute,
    supplyChallanRoute,
    SupplyChallanUpdateComponent
} from './';

const ENTITY_STATES = [...supplyChallanRoute, ...supplyChallanPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplyChallanComponent,
        SupplyChallanDetailComponent,
        SupplyChallanUpdateComponent,
        SupplyChallanDeleteDialogComponent,
        SupplyChallanDeletePopupComponent
    ],
    entryComponents: [
        SupplyChallanComponent,
        SupplyChallanUpdateComponent,
        SupplyChallanDeleteDialogComponent,
        SupplyChallanDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSupplyChallanModule {}
