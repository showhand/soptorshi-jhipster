import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    VendorContactPersonComponent,
    VendorContactPersonDetailComponent,
    VendorContactPersonUpdateComponent,
    VendorContactPersonDeletePopupComponent,
    VendorContactPersonDeleteDialogComponent,
    vendorContactPersonRoute,
    vendorContactPersonPopupRoute
} from './';

const ENTITY_STATES = [...vendorContactPersonRoute, ...vendorContactPersonPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /* declarations: [
        VendorContactPersonComponent,
        VendorContactPersonDetailComponent,
        VendorContactPersonUpdateComponent,
        VendorContactPersonDeleteDialogComponent,
        VendorContactPersonDeletePopupComponent
    ],
    entryComponents: [
        VendorContactPersonComponent,
        VendorContactPersonUpdateComponent,
        VendorContactPersonDeleteDialogComponent,
        VendorContactPersonDeletePopupComponent
    ],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiVendorContactPersonModule {}
