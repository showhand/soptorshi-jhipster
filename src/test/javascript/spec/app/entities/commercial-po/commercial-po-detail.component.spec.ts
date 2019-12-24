/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialPoDetailComponent } from 'app/entities/commercial-po/commercial-po-detail.component';
import { CommercialPo } from 'app/shared/model/commercial-po.model';

describe('Component Tests', () => {
    describe('CommercialPo Management Detail Component', () => {
        let comp: CommercialPoDetailComponent;
        let fixture: ComponentFixture<CommercialPoDetailComponent>;
        const route = ({ data: of({ commercialPo: new CommercialPo(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialPoDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CommercialPoDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialPoDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.commercialPo).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
