import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SoptorshiSharedModule } from 'app/shared';
import {
    SupplyShopDeleteDialogExtendedComponent,
    SupplyShopDeletePopupExtendedComponent,
    SupplyShopDetailExtendedComponent,
    SupplyShopExtendedComponent,
    supplyShopExtendedRoute,
    supplyShopPopupExtendedRoute,
    SupplyShopUpdateExtendedComponent
} from './';

const ENTITY_STATES = [...supplyShopExtendedRoute, ...supplyShopPopupExtendedRoute];

@NgModule({
    imports: [SoptorshiSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SupplyShopExtendedComponent,
        SupplyShopDetailExtendedComponent,
        SupplyShopUpdateExtendedComponent,
        SupplyShopDeleteDialogExtendedComponent,
        SupplyShopDeletePopupExtendedComponent
    ],
    entryComponents: [
        SupplyShopExtendedComponent,
        SupplyShopUpdateExtendedComponent,
        SupplyShopDeleteDialogExtendedComponent,
        SupplyShopDeletePopupExtendedComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SoptorshiSupplyShopExtendedModule {}
