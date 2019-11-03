/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialWorkOrderDetailsDetailComponent } from 'app/entities/commercial-work-order-details/commercial-work-order-details-detail.component';
import { CommercialWorkOrderDetails } from 'app/shared/model/commercial-work-order-details.model';

describe('Component Tests', () => {
    describe('CommercialWorkOrderDetails Management Detail Component', () => {
        let comp: CommercialWorkOrderDetailsDetailComponent;
        let fixture: ComponentFixture<CommercialWorkOrderDetailsDetailComponent>;
        const route = ({ data: of({ commercialWorkOrderDetails: new CommercialWorkOrderDetails(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialWorkOrderDetailsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CommercialWorkOrderDetailsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialWorkOrderDetailsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.commercialWorkOrderDetails).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
