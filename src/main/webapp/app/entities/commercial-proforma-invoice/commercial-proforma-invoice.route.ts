import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { CommercialProformaInvoice, ICommercialProformaInvoice } from 'app/shared/model/commercial-proforma-invoice.model';
import { CommercialProformaInvoiceService } from './commercial-proforma-invoice.service';
import { CommercialProformaInvoiceComponent } from './commercial-proforma-invoice.component';
import { CommercialProformaInvoiceDetailComponent } from './commercial-proforma-invoice-detail.component';
import { CommercialProformaInvoiceUpdateComponent } from './commercial-proforma-invoice-update.component';
import { CommercialProformaInvoiceDeletePopupComponent } from './commercial-proforma-invoice-delete-dialog.component';

@Injectable({ providedIn: 'root' })
export class CommercialProformaInvoiceResolve implements Resolve<ICommercialProformaInvoice> {
    constructor(private service: CommercialProformaInvoiceService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICommercialProformaInvoice> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<CommercialProformaInvoice>) => response.ok),
                map((commercialProformaInvoice: HttpResponse<CommercialProformaInvoice>) => commercialProformaInvoice.body)
            );
        }
        return of(new CommercialProformaInvoice());
    }
}

export const commercialProformaInvoiceRoute: Routes = [
    {
        path: '',
        component: CommercialProformaInvoiceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialProformaInvoices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommercialProformaInvoiceDetailComponent,
        resolve: {
            commercialProformaInvoice: CommercialProformaInvoiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialProformaInvoices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommercialProformaInvoiceUpdateComponent,
        resolve: {
            commercialProformaInvoice: CommercialProformaInvoiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialProformaInvoices'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommercialProformaInvoiceUpdateComponent,
        resolve: {
            commercialProformaInvoice: CommercialProformaInvoiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialProformaInvoices'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commercialProformaInvoicePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CommercialProformaInvoiceDeletePopupComponent,
        resolve: {
            commercialProformaInvoice: CommercialProformaInvoiceResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CommercialProformaInvoices'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
