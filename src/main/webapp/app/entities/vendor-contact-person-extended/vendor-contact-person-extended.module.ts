import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import { VendorContactPersonDeleteDialogComponent, VendorContactPersonDeletePopupComponent } from 'app/entities/vendor-contact-person';
import { VendorContactPersonExtendedUpdateComponent } from 'app/entities/vendor-contact-person-extended/vendor-contact-person-extended-update.component';
import { VendorContactPersonExtendedDetailComponent } from 'app/entities/vendor-contact-person-extended/vendor-contact-person-extended-detail.component';
import { VendorContactPersonExtendedComponent } from 'app/entities/vendor-contact-person-extended/vendor-contact-person-extended.component';
import {
    vendorContactPersonExtendedPopupRoute,
    vendorContactPersonExtendedRoute
} from 'app/entities/vendor-contact-person-extended/vendor-contact-person-extended.route';

const ENTITY_STATES = [...vendorContactPersonExtendedRoute, ...vendorContactPersonExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VendorContactPersonExtendedComponent,
        VendorContactPersonExtendedDetailComponent,
        VendorContactPersonExtendedUpdateComponent,
        VendorContactPersonDeleteDialogComponent,
        VendorContactPersonDeletePopupComponent
    ],
    entryComponents: [
        VendorContactPersonExtendedComponent,
        VendorContactPersonExtendedUpdateComponent,
        VendorContactPersonDeleteDialogComponent,
        VendorContactPersonDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiVendorContactPersonExtendedModule {}
