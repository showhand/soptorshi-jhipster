import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import { vendorPopupRoute, vendorRoute } from 'app/entities/vendor/vendor.route';

const ENTITY_STATES = [...vendorRoute, ...vendorPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    /*declarations: [VendorComponent, VendorDetailComponent, VendorUpdateComponent, VendorDeleteDialogComponent, VendorDeletePopupComponent],
    entryComponents: [VendorComponent, VendorUpdateComponent, VendorDeleteDialogComponent, VendorDeletePopupComponent],*/
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiVendorModule {}
