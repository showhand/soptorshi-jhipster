import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    VendorComponent,
    VendorDeleteDialogComponent,
    VendorDeletePopupComponent,
    VendorDetailComponent,
    VendorUpdateComponent
} from 'app/entities/vendor';
import { VendorExtendedUpdateComponent } from 'app/entities/vendor-extended/vendor-extended-update.component';
import { VendorExtendedDetailComponent } from 'app/entities/vendor-extended/vendor-extended-detail.component';
import { VendorExtendedComponent } from 'app/entities/vendor-extended/vendor-extended.component';
import { vendorExtendedPopupRoute, vendorExtendedRoute } from 'app/entities/vendor-extended/vendor-extended.route';
import {
    VendorExtendedUpdateDialogComponent,
    VendorExtendedUpdatedPopupComponent
} from 'app/entities/vendor-extended/vendor-extended-update-dialog.component';
import { SoptorshiVendorContactPersonExtendedModule } from 'app/entities/vendor-contact-person-extended/vendor-contact-person-extended.module';
import { VendorContactPersonExtendedDirectiveComponent } from 'app/entities/vendor-contact-person-extended/vendor-contact-person-extended-directive.component';

const ENTITY_STATES = [...vendorExtendedRoute, ...vendorExtendedPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        VendorComponent,
        VendorDetailComponent,
        VendorUpdateComponent,
        VendorExtendedComponent,
        VendorExtendedDetailComponent,
        VendorExtendedUpdateComponent,
        VendorDeleteDialogComponent,
        VendorDeletePopupComponent,
        VendorExtendedUpdateDialogComponent,
        VendorExtendedUpdatedPopupComponent,
        VendorContactPersonExtendedDirectiveComponent
    ],
    entryComponents: [
        VendorExtendedComponent,
        VendorExtendedDetailComponent,
        VendorExtendedUpdateComponent,
        VendorDeleteDialogComponent,
        VendorDeletePopupComponent,
        VendorExtendedUpdateDialogComponent,
        VendorExtendedUpdatedPopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiVendorExtendedModule {}
