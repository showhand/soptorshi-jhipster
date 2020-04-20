import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplyShopComponent,
    SupplyShopDeleteDialogComponent,
    SupplyShopDeletePopupComponent,
    SupplyShopDetailComponent,
    supplyShopPopupRoute,
    supplyShopRoute,
    SupplyShopUpdateComponent
} from './';

const ENTITY_STATES = [...supplyShopRoute, ...supplyShopPopupRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplyShopComponent,
        SupplyShopDetailComponent,
        SupplyShopUpdateComponent,
        SupplyShopDeleteDialogComponent,
        SupplyShopDeletePopupComponent
    ],
    entryComponents: [SupplyShopComponent, SupplyShopUpdateComponent, SupplyShopDeleteDialogComponent, SupplyShopDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSupplyShopModule {}
