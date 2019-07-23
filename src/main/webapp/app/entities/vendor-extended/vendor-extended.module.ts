import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import { VendorDeleteDialogComponent, VendorDeletePopupComponent } from 'app/entities/vendor';
import { VendorExtendedUpdateComponent } from 'app/entities/vendor-extended/vendor-extended-update.component';
import { VendorExtendedDetailComponent } from 'app/entities/vendor-extended/vendor-extended-detail.component';
import { VendorExtendedComponent } from 'app/entities/vendor-extended/vendor-extended.component';
import { vendorExtendedPopupRoute, vendorExtendedRoute } from 'app/entities/vendor-extended/vendor-extended.route';

const ENTITY_STATES = [...vendorExtendedRoute, ...vendorExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VendorExtendedComponent,
        VendorExtendedDetailComponent,
        VendorExtendedUpdateComponent,
        VendorDeleteDialogComponent,
        VendorDeletePopupComponent
    ],
    entryComponents: [
        VendorExtendedComponent,
        VendorExtendedDetailComponent,
        VendorExtendedUpdateComponent,
        VendorDeleteDialogComponent,
        VendorDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiVendorExtendedModule {}
