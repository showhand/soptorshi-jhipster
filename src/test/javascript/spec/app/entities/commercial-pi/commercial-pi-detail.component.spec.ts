/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { CommercialPiDetailComponent } from 'app/entities/commercial-pi/commercial-pi-detail.component';
import { CommercialPi } from 'app/shared/model/commercial-pi.model';

describe('Component Tests', () => {
    describe('CommercialPi Management Detail Component', () => {
        let comp: CommercialPiDetailComponent;
        let fixture: ComponentFixture<CommercialPiDetailComponent>;
        const route = ({ data: of({ commercialPi: new CommercialPi(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [CommercialPiDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CommercialPiDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommercialPiDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.commercialPi).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
