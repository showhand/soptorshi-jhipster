/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialPackagingDetailsDetailComponent } from 'app/entities/commercial-packaging-details/commercial-packaging-details-detail.component';
import { CommercialPackagingDetails } from 'app/shared/model/commercial-packaging-details.model';

describe('Component Tests', () => {
    describe('CommercialPackagingDetails Management Detail Component', () => {
        let comp: CommercialPackagingDetailsDetailComponent;
        let fixture: ComponentFixture<CommercialPackagingDetailsDetailComponent>;
        const route = ({ data: of({ commercialPackagingDetails: new CommercialPackagingDetails(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialPackagingDetailsDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CommercialPackagingDetailsDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialPackagingDetailsDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.commercialPackagingDetails).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
