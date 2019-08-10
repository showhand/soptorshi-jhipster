/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SoptorshiTestModule } from '../../../test.module';
import { ManufacturerDetailComponent } from 'app/entities/manufacturer/manufacturer-detail.component';
import { Manufacturer } from 'app/shared/model/manufacturer.model';

describe('Component Tests', () => {
    describe('Manufacturer Management Detail Component', () => {
        let comp: ManufacturerDetailComponent;
        let fixture: ComponentFixture<ManufacturerDetailComponent>;
        const route = ({ data: of({ manufacturer: new Manufacturer(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SoptorshiTestModule],
                declarations: [ManufacturerDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ManufacturerDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ManufacturerDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.manufacturer).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
