import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

interface Tender {
  id: string;
  title: string;
  entity: string;
  amount: string;
  deadline: string;
  category: string;
  cpv: string;
}

interface Feature {
  icon: string;
  title: string;
  description: string;
}

interface Testimonial {
  name: string;
  role: string;
  company: string;
  content: string;
}

@Component({
  selector: 'app-home',
  imports: [CommonModule, FormsModule],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  searchQuery = '';

  recentTenders: Tender[] = [
    {
      id: '2024-001',
      title: 'Suministro de equipos informáticos para administración local',
      entity: 'Ayuntamiento de Madrid',
      amount: '€450,000',
      deadline: '15 Feb 2024',
      category: 'Tecnología',
      cpv: '30200000-1',
    },
    {
      id: '2024-002',
      title: 'Servicios de consultoría en transformación digital',
      entity: 'Generalitat de Catalunya',
      amount: '€280,000',
      deadline: '22 Feb 2024',
      category: 'Servicios',
      cpv: '72000000-5',
    },
    {
      id: '2024-003',
      title: 'Obras de rehabilitación de infraestructuras públicas',
      entity: 'Junta de Andalucía',
      amount: '€1,200,000',
      deadline: '28 Feb 2024',
      category: 'Obras',
      cpv: '45000000-7',
    },
  ];

  features: Feature[] = [
    {
      icon: 'brain',
      title: 'IA Inteligente',
      description: 'Análisis automático de pliegos y detección de oportunidades relevantes para tu negocio',
    },
    {
      icon: 'bell',
      title: 'Alertas Personalizadas',
      description: 'Notificaciones segmentadas por CPV, sector, importe y palabras clave específicas',
    },
    {
      icon: 'file-text',
      title: 'CRM de Licitaciones',
      description: 'Gestiona tus oportunidades con seguimiento de estados, etiquetado y asignaciones',
    },
    {
      icon: 'trending-up',
      title: 'Informes y Analytics',
      description: 'Reportes visuales de actividad, análisis de competencia y tendencias del mercado',
    },
  ];

  testimonials: Testimonial[] = [
    {
      name: 'María González',
      role: 'Directora Comercial',
      company: 'TechSolutions SL',
      content:
        'Hemos aumentado un 40% nuestras adjudicaciones gracias a las alertas inteligentes y el análisis de competencia.',
    },
    {
      name: 'Carlos Ruiz',
      role: 'CEO',
      company: 'Construcciones Norte',
      content: 'La plataforma nos ahorra 15 horas semanales en búsqueda y análisis de licitaciones. Imprescindible.',
    },
  ];

  stats = [
    { value: '50K+', label: 'Licitaciones analizadas' },
    { value: '1,200+', label: 'Empresas activas' },
    { value: '€2.5B', label: 'En adjudicaciones' },
    { value: '98%', label: 'Satisfacción cliente' },
  ];

  onSearch(): void {
    console.log('Searching for:', this.searchQuery);
    // Implementar lógica de búsqueda
  }

  onStartFree(): void {
    console.log('Starting free trial');
    // Implementar navegación o modal
  }

  onRequestDemo(): void {
    console.log('Requesting demo');
    // Implementar navegación o modal
  }

  onViewTender(tender: Tender): void {
    console.log('Viewing tender:', tender.id);
    // Implementar navegación a detalle
  }

  onLogin(): void {
    console.log('Login clicked');
    // Implementar navegación a login
  }

  onViewAllTenders(): void {
    console.log('View all tenders');
    // Implementar navegación a listado completo
  }

  onContactSales(): void {
    console.log('Contact sales');
    // Implementar navegación o modal de contacto
  }
}
