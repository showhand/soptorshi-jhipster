import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { VendorContactPerson } from 'app/shared/model/vendor-contact-person.model';
import { IVendorContactPerson } from 'app/shared/model/vendor-contact-person.model';
import {
    VendorContactPersonDeletePopupComponent,
    VendorContactPersonResolve,
    VendorContactPersonService
} from 'app/entities/vendor-contact-person';
import { VendorContactPersonExtendedUpdateComponent } from 'app/entities/vendor-contact-person-extended/vendor-contact-person-extended-update.component';
import { VendorContactPersonExtendedDetailComponent } from 'app/entities/vendor-contact-person-extended/vendor-contact-person-extended-detail.component';
import { VendorContactPersonExtendedComponent } from 'app/entities/vendor-contact-person-extended/vendor-contact-person-extended.component';

@Injectable({ providedIn: 'root' })
export class VendorContactPersonExtendedResolve extends VendorContactPersonResolve {
    constructor(public service: VendorContactPersonService) {
        super(service);
    }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IVendorContactPerson> {
        const id = route.params['id'] ? route.params['id'] : null;
        const vendorId = route.params['vendorId'] ? route.params['vendorId'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<VendorContactPerson>) => response.ok),
                map((vendorContactPerson: HttpResponse<VendorContactPerson>) => vendorContactPerson.body)
            );
        } else if (vendorId) {
            const vendorContactPerson = new VendorContactPerson();
            vendorContactPerson.vendorId = vendorId;
            return of(vendorContactPerson);
        }
        return of(new VendorContactPerson());
    }
}

export const vendorContactPersonExtendedRoute: Routes = [
    {
        path: '',
        component: VendorContactPersonExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_REQUISITION', 'ROLE_PURCHASE_COMMITTEE', 'ROLE_CFO'],
            defaultSort: 'id,asc',
            pageTitle: 'VendorContactPeople'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':vendorId/vendor',
        component: VendorContactPersonExtendedComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams,
            vendorContactPerson: VendorContactPersonExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_REQUISITION', 'ROLE_PURCHASE_COMMITTEE', 'ROLE_CFO'],
            defaultSort: 'id,asc',
            pageTitle: 'VendorContactPeople'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: VendorContactPersonExtendedDetailComponent,
        resolve: {
            vendorContactPerson: VendorContactPersonExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_REQUISITION', 'ROLE_PURCHASE_COMMITTEE', 'ROLE_CFO'],
            pageTitle: 'VendorContactPeople'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':vendorId/new',
        component: VendorContactPersonExtendedUpdateComponent,
        resolve: {
            vendorContactPerson: VendorContactPersonExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_REQUISITION', 'ROLE_PURCHASE_COMMITTEE', 'ROLE_CFO'],
            pageTitle: 'VendorContactPeople'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: VendorContactPersonExtendedUpdateComponent,
        resolve: {
            vendorContactPerson: VendorContactPersonExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_REQUISITION', 'ROLE_PURCHASE_COMMITTEE', 'ROLE_CFO'],
            pageTitle: 'VendorContactPeople'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: VendorContactPersonExtendedUpdateComponent,
        resolve: {
            vendorContactPerson: VendorContactPersonExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_REQUISITION', 'ROLE_PURCHASE_COMMITTEE', 'ROLE_CFO'],
            pageTitle: 'VendorContactPeople'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const vendorContactPersonExtendedPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: VendorContactPersonDeletePopupComponent,
        resolve: {
            vendorContactPerson: VendorContactPersonExtendedResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_REQUISITION', 'ROLE_PURCHASE_COMMITTEE', 'ROLE_CFO'],
            pageTitle: 'VendorContactPeople'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
